package com.gentics.mesh.core.verticle;

import static com.gentics.mesh.demo.TestDataProvider.PROJECT_NAME;
import static com.gentics.mesh.http.HttpConstants.ETAG;
import static com.gentics.mesh.mock.Mocks.getMockedInternalActionContext;
import static com.gentics.mesh.util.MeshAssert.assertSuccess;
import static com.gentics.mesh.util.MeshAssert.latchFor;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.gentics.mesh.FieldUtil;
import com.gentics.mesh.core.AbstractSpringVerticle;
import com.gentics.mesh.core.data.node.Node;
import com.gentics.mesh.core.data.schema.SchemaContainer;
import com.gentics.mesh.core.rest.common.GenericMessageResponse;
import com.gentics.mesh.core.rest.node.WebRootResponse;
import com.gentics.mesh.core.rest.schema.Schema;
import com.gentics.mesh.core.verticle.node.NodeVerticle;
import com.gentics.mesh.core.verticle.webroot.WebRootVerticle;
import com.gentics.mesh.graphdb.NoTx;
import com.gentics.mesh.parameter.impl.LinkType;
import com.gentics.mesh.parameter.impl.NodeParameters;
import com.gentics.mesh.parameter.impl.VersioningParameters;
import com.gentics.mesh.rest.client.MeshRequest;
import com.gentics.mesh.rest.client.MeshResponse;
import com.gentics.mesh.test.AbstractETagTest;

public class WebRootVerticleETagTest extends AbstractETagTest {

	@Autowired
	private WebRootVerticle webrootVerticle;

	@Autowired
	private NodeVerticle nodeVerticle;

	@Override
	public List<AbstractSpringVerticle> getAdditionalVertices() {
		List<AbstractSpringVerticle> list = new ArrayList<>();
		list.add(webrootVerticle);
		list.add(nodeVerticle);
		return list;
	}

	@Test
	public void testReadBinaryNode() throws IOException {
		try (NoTx noTrx = db.noTx()) {
			Node node = content("news_2015");

			// 1. Transform the node into a binary content
			SchemaContainer container = schemaContainer("binary-content");
			node.setSchemaContainer(container);
			node.getLatestDraftFieldContainer(english()).setSchemaContainerVersion(container.getLatestVersion());
			prepareSchema(node, "image/*", "binary");
			String contentType = "application/octet-stream";
			int binaryLen = 8000;
			String fileName = "somefile.dat";

			// 2. Update the binary data
			MeshResponse<GenericMessageResponse> future = uploadRandomData(node.getUuid(), "en", "binary", binaryLen, contentType, fileName).invoke();
			latchFor(future);
			assertSuccess(future);
			expectResponseMessage(future, "node_binary_field_updated", "binary");

			// 3. Try to resolve the path
			String path = "/News/2015/somefile.dat";
			MeshResponse<WebRootResponse> response = getClient()
					.webroot(PROJECT_NAME, path, new VersioningParameters().draft(), new NodeParameters().setResolveLinks(LinkType.FULL)).invoke();

			latchFor(response);
			String etag = response.getResponse().getHeader(ETAG);
			assertNotNull(etag);

			// Check whether 304 is returned for correct etag
			MeshRequest<WebRootResponse> request = getClient().webroot(PROJECT_NAME, path, new VersioningParameters().draft(),
					new NodeParameters().setResolveLinks(LinkType.FULL));
			assertEquals(etag, expect304(request, etag));

		}

	}

	@Test
	@Override
	public void testReadOne() {
		try (NoTx noTx = db.noTx()) {
			String path = "/News/2015/News_2015.en.html";
			Node node = content("news_2015");

			// Inject the reference node field
			Schema schema = node.getGraphFieldContainer("en").getSchemaContainerVersion().getSchema();
			schema.addField(FieldUtil.createNodeFieldSchema("reference"));
			node.getGraphFieldContainer("en").getSchemaContainerVersion().setSchema(schema);
			node.getGraphFieldContainer("en").createNode("reference", folder("2015"));

			MeshResponse<WebRootResponse> response = getClient()
					.webroot(PROJECT_NAME, path, new VersioningParameters().draft(), new NodeParameters().setLanguages("en", "de")).invoke();
			latchFor(response);
			String etag = node.getETag(getMockedInternalActionContext());
			assertEquals(etag, response.getResponse().getHeader(ETAG));

			// Check whether 304 is returned for correct etag
			MeshRequest<WebRootResponse> request = getClient().webroot(PROJECT_NAME, path, new VersioningParameters().draft(),
					new NodeParameters().setLanguages("en", "de"));
			assertEquals(etag, expect304(request, etag));

		}

	}

	@Override
	public void testReadMultiple() {
		// TODO Auto-generated method stub

	}

}