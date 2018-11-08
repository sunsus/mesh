package com.gentics.mesh.handler;

import io.vertx.ext.web.RoutingContext;

public interface RangeRequestHandler {

	/**
	 * Default of whether vary header should be sent.
	 */
	boolean DEFAULT_SEND_VARY_HEADER = true;

	/**
	 * Default of whether async filesystem access should always be used
	 */
	boolean DEFAULT_ALWAYS_ASYNC_FS = false;

	/**
	 * Default of whether cache header handling is enabled
	 */
	boolean DEFAULT_CACHING_ENABLED = true;

	/**
	 * Default of whether fs async/sync tuning should be used
	 */
	boolean DEFAULT_ENABLE_FS_TUNING = true;

	/**
	 * The default max cache size
	 */
	int DEFAULT_MAX_CACHE_SIZE = 10000;

	/**
	 * Default cache entry timeout, when caching
	 */
	long DEFAULT_CACHE_ENTRY_TIMEOUT = 30000; // 30 seconds

	/**
	 * Default max avg serve time, in ns, over which serving will be considered slow
	 */
	long DEFAULT_MAX_AVG_SERVE_TIME_NS = 1000000; // 1ms

	/**
	 * Default max age for cache headers
	 */
	long DEFAULT_MAX_AGE_SECONDS = 86400; // One day

	/**
	 * Default value of whether files are read -only and never will be updated
	 */
	boolean DEFAULT_FILES_READ_ONLY = true;

	/**
	 * Process the request for the requested binary file.
	 * 
	 * @param rc
	 * @param localPath
	 * @param contentType
	 */
	void handle(RoutingContext rc, String localPath, String contentType);

}
