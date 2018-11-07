package com.gentics.mesh.handler.impl;

import static io.netty.handler.codec.http.HttpResponseStatus.NOT_MODIFIED;
import static io.netty.handler.codec.http.HttpResponseStatus.PARTIAL_CONTENT;
import static io.netty.handler.codec.http.HttpResponseStatus.REQUESTED_RANGE_NOT_SATISFIABLE;

import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.gentics.mesh.handler.RangeRequestHandler;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.MultiMap;
import io.vertx.core.VertxException;
import io.vertx.core.file.FileProps;
import io.vertx.core.file.FileSystem;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.http.HttpVersion;
import io.vertx.core.http.impl.MimeMapping;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.Http2PushMapping;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.impl.LRUCache;
import io.vertx.ext.web.impl.Utils;

public class RangeRequestHandlerImpl implements RangeRequestHandler {

	private static final Logger log = LoggerFactory.getLogger(RangeRequestHandlerImpl.class);

	private static int NUM_SERVES_TUNING_FS_ACCESS = 1000;

	private Map<String, CacheEntry> propsCache;

	private boolean filesReadOnly = DEFAULT_FILES_READ_ONLY;
	private boolean tuning = DEFAULT_ENABLE_FS_TUNING;
	private static final Pattern RANGE = Pattern.compile("^bytes=(\\d+)-(\\d*)$");
	private final DateFormat dateTimeFormatter = Utils.createRFC1123DateTimeFormatter();
	private String defaultContentEncoding = Charset.defaultCharset().name();
	private List<Http2PushMapping> http2PushMappings;
	private boolean sendVaryHeader = DEFAULT_SEND_VARY_HEADER;
	private boolean cachingEnabled = DEFAULT_CACHING_ENABLED;
	private long cacheEntryTimeout = DEFAULT_CACHE_ENTRY_TIMEOUT;
	private long maxAgeSeconds = DEFAULT_MAX_AGE_SECONDS; // One day
	private int maxCacheSize = DEFAULT_MAX_CACHE_SIZE;

	// These members are all related to auto tuning of synchronous vs asynchronous file system access
	private boolean alwaysAsyncFS = DEFAULT_ALWAYS_ASYNC_FS;
	private long maxAvgServeTimeNanoSeconds = DEFAULT_MAX_AVG_SERVE_TIME_NS;
	private long totalTime;
	private long numServesBlocking;
	private boolean useAsyncFS;
	private long nextAvgCheck = NUM_SERVES_TUNING_FS_ACCESS;

	@Override
	public void handle(RoutingContext context, String localPath) {
		sendStatic(context, localPath);
		// HttpServerResponse response = rc.response();
		// response.putHeader(HttpHeaders.CONTENT_LENGTH, contentLength);
		// response.sendFile(localPath);
	}

	private void sendStatic(RoutingContext context, String path) {

		String file = null;

		// Look in cache
		CacheEntry entry;
		if (cachingEnabled) {
			entry = propsCache().get(path);
			if (entry != null) {
				HttpServerRequest request = context.request();
				if ((filesReadOnly || !entry.isOutOfDate()) && entry.shouldUseCached(request)) {
					context.response().setStatusCode(NOT_MODIFIED.code()).end();
					return;
				}
			}
		}

		if (file == null) {
			file = path;
		}

		final String sfile = file;

		// verify if the file exists
		isFileExisting(context, sfile, exists -> {
			if (exists.failed()) {
				context.fail(exists.cause());
				return;
			}

			// file does not exist, continue...
			if (!exists.result()) {
				context.next();
				return;
			}

			// Need to read the props from the filesystem
			getFileProps(context, sfile, res -> {
				if (res.succeeded()) {
					FileProps fprops = res.result();
					if (fprops == null) {
						// File does not exist
						context.next();
					} else {
						propsCache().put(path, new CacheEntry(fprops, System.currentTimeMillis()));
						sendFile(context, sfile, fprops);
					}
				} else {
					context.fail(res.cause());
				}
			});
		});
	}

	private Map<String, CacheEntry> propsCache() {
		if (propsCache == null) {
			propsCache = new LRUCache<>(maxCacheSize);
		}
		return propsCache;
	}

	private void sendFile(RoutingContext context, String file, FileProps fileProps) {
		HttpServerRequest request = context.request();

		Long offset = null;
		Long end = null;
		MultiMap headers = null;

		// check if the client is making a range request
		String range = request.getHeader("Range");
		// end byte is length - 1
		end = fileProps.size() - 1;

		if (range != null) {
			Matcher m = RANGE.matcher(range);
			if (m.matches()) {
				try {
					String part = m.group(1);
					// offset cannot be empty
					offset = Long.parseLong(part);
					// offset must fall inside the limits of the file
					if (offset < 0 || offset >= fileProps.size()) {
						throw new IndexOutOfBoundsException();
					}
					// length can be empty
					part = m.group(2);
					if (part != null && part.length() > 0) {
						// ranges are inclusive
						end = Math.min(end, Long.parseLong(part));
						// end offset must not be smaller than start offset
						if (end < offset) {
							throw new IndexOutOfBoundsException();
						}
					}
				} catch (NumberFormatException | IndexOutOfBoundsException e) {
					context.response().putHeader("Content-Range", "bytes */" + fileProps.size());
					context.fail(REQUESTED_RANGE_NOT_SATISFIABLE.code());
					return;
				}
			}

			// notify client we support range requests
			headers = request.response().headers();
			headers.set("Accept-Ranges", "bytes");
			// send the content length even for HEAD requests
			headers.set("Content-Length", Long.toString(end + 1 - (offset == null ? 0 : offset)));
		}

		writeCacheHeaders(request, fileProps);

		if (request.method() == HttpMethod.HEAD) {
			request.response().end();
		} else {
			if (offset != null) {
				// must return content range
				headers.set("Content-Range", "bytes " + offset + "-" + end + "/" + fileProps.size());
				// return a partial response
				request.response().setStatusCode(PARTIAL_CONTENT.code());

				final Long finalOffset = offset;
				final Long finalEnd = end;
				// guess content type
				String contentType = MimeMapping.getMimeTypeForFilename(file);
				if (contentType != null) {
					if (contentType.startsWith("text")) {
						request.response().putHeader("Content-Type", contentType + ";charset=" + defaultContentEncoding);
					} else {
						request.response().putHeader("Content-Type", contentType);
					}
				}

				request.response().sendFile(file, finalOffset, finalEnd + 1, res2 -> {
					if (res2.failed()) {
						context.fail(res2.cause());
					}
				});
			} else {
				// guess content type
				String contentType = MimeMapping.getMimeTypeForFilename(file);
				if (contentType != null) {
					if (contentType.startsWith("text")) {
						request.response().putHeader("Content-Type", contentType + ";charset=" + defaultContentEncoding);
					} else {
						request.response().putHeader("Content-Type", contentType);
					}
				}

				// http2 pushing support
				if (request.version() == HttpVersion.HTTP_2 && http2PushMappings != null) {
					for (Http2PushMapping dependency : http2PushMappings) {
						if (!dependency.isNoPush()) {
							final String dep = dependency.getFilePath();
							HttpServerResponse response = request.response();

							// get the file props
							getFileProps(context, dep, filePropsAsyncResult -> {
								if (filePropsAsyncResult.succeeded()) {
									// push
									writeCacheHeaders(request, filePropsAsyncResult.result());
									response.push(HttpMethod.GET, "/" + dependency.getFilePath(), pushAsyncResult -> {
										if (pushAsyncResult.succeeded()) {
											HttpServerResponse res = pushAsyncResult.result();
											final String depContentType = MimeMapping.getMimeTypeForExtension(file);
											if (depContentType != null) {
												if (depContentType.startsWith("text")) {
													res.putHeader("Content-Type", contentType + ";charset=" + defaultContentEncoding);
												} else {
													res.putHeader("Content-Type", contentType);
												}
											}
											res.sendFile(dependency.getFilePath());
										}
									});
								}
							});
						}
					}

				} else if (http2PushMappings != null) {
					// Link preload when file push is not supported
					HttpServerResponse response = request.response();
					List<String> links = new ArrayList<>();
					for (Http2PushMapping dependency : http2PushMappings) {
						final String dep = dependency.getFilePath();
						// get the file props
						getFileProps(context, dep, filePropsAsyncResult -> {
							if (filePropsAsyncResult.succeeded()) {
								// push
								writeCacheHeaders(request, filePropsAsyncResult.result());
								links.add("<" + dependency.getFilePath() + ">; rel=preload; as="
									+ dependency.getExtensionTarget() + (dependency.isNoPush() ? "; nopush" : ""));
							}
						});
					}
					response.putHeader("Link", links);
				}

				request.response().sendFile(file, res2 -> {
					if (res2.failed()) {
						context.fail(res2.cause());
					}
				});
			}
		}
	}

	private synchronized void getFileProps(RoutingContext context, String file, Handler<AsyncResult<FileProps>> resultHandler) {
		FileSystem fs = context.vertx().fileSystem();
		if (alwaysAsyncFS || useAsyncFS) {
			fs.props(file, resultHandler);
		} else {
			// Use synchronous access - it might well be faster!
			long start = 0;
			if (tuning) {
				start = System.nanoTime();
			}
			try {
				FileProps props = fs.propsBlocking(file);

				if (tuning) {
					long end = System.nanoTime();
					long dur = end - start;
					totalTime += dur;
					numServesBlocking++;
					if (numServesBlocking == Long.MAX_VALUE) {
						// Unlikely.. but...
						resetTuning();
					} else if (numServesBlocking == nextAvgCheck) {
						double avg = (double) totalTime / numServesBlocking;
						if (avg > maxAvgServeTimeNanoSeconds) {
							useAsyncFS = true;
							log.info("Switching to async file system access in static file server as fs access is slow! (Average access time of "
								+ avg + " ns)");
							tuning = false;
						}
						nextAvgCheck += NUM_SERVES_TUNING_FS_ACCESS;
					}
				}
				resultHandler.handle(Future.succeededFuture(props));
			} catch (RuntimeException e) {
				resultHandler.handle(Future.failedFuture(e.getCause()));
			}
		}
	}

	private synchronized void isFileExisting(RoutingContext context, String file, Handler<AsyncResult<Boolean>> resultHandler) {
		FileSystem fs = context.vertx().fileSystem();
		fs.exists(file, resultHandler);
	}

	/**
	 * Create all required header so content can be cache by Caching servers or Browsers
	 *
	 * @param request
	 *            base HttpServerRequest
	 * @param props
	 *            file properties
	 */
	private void writeCacheHeaders(HttpServerRequest request, FileProps props) {

		MultiMap headers = request.response().headers();

		if (cachingEnabled) {
			// We use cache-control and last-modified
			// We *do not use* etags and expires (since they do the same thing - redundant)
			headers.set("cache-control", "public, max-age=" + maxAgeSeconds);
			headers.set("last-modified", dateTimeFormatter.format(props.lastModifiedTime()));
			// We send the vary header (for intermediate caches)
			// (assumes that most will turn on compression when using static handler)
			if (sendVaryHeader && request.headers().contains("accept-encoding")) {
				headers.set("vary", "accept-encoding");
			}
		}

		// date header is mandatory
		headers.set("date", dateTimeFormatter.format(new Date()));
	}

	private void resetTuning() {
		// Reset
		nextAvgCheck = NUM_SERVES_TUNING_FS_ACCESS;
		totalTime = 0;
		numServesBlocking = 0;
	}

	private Date parseDate(String header) {
		try {
			return dateTimeFormatter.parse(header);
		} catch (ParseException e) {
			throw new VertxException(e);
		}
	}

	private final class CacheEntry {
		final FileProps props;
		long createDate;

		private CacheEntry(FileProps props, long createDate) {
			this.props = props;
			this.createDate = createDate;
		}

		// return true if there are conditional headers present and they match what is in the entry
		boolean shouldUseCached(HttpServerRequest request) {
			String ifModifiedSince = request.headers().get("if-modified-since");
			if (ifModifiedSince == null) {
				// Not a conditional request
				return false;
			}
			Date ifModifiedSinceDate = parseDate(ifModifiedSince);
			boolean modifiedSince = Utils.secondsFactor(props.lastModifiedTime()) > ifModifiedSinceDate.getTime();
			return !modifiedSince;
		}

		boolean isOutOfDate() {
			return System.currentTimeMillis() - createDate > cacheEntryTimeout;
		}

	}

}
