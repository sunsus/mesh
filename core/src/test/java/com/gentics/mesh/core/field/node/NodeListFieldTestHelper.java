package com.gentics.mesh.core.field.node;

import com.gentics.mesh.core.data.node.field.list.NodeGraphFieldList;
import com.gentics.mesh.core.field.DataProvider;
import com.gentics.mesh.core.field.FieldFetcher;
import com.gentics.mesh.test.TestFullDataProvider;

public interface NodeListFieldTestHelper {


	static final DataProvider FILL = (container, name) -> {
		NodeGraphFieldList	list = container.createNodeList(name);
		list.addItem(list.createNode("0", TestFullDataProvider.getInstance().getFolder("2015")));
		list.addItem(list.createNode("1", TestFullDataProvider.getInstance().getFolder("2014")));
		list.addItem(list.createNode("2", TestFullDataProvider.getInstance().getFolder("news")));
	};

	static final DataProvider CREATE_EMPTY = (container, name) -> {
	};

	static final FieldFetcher FETCH = (container, name) -> container.getNodeList(name);

}
