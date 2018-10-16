package com.gentics.diktyo.orientdb.domain;

import com.gentics.diktyo.orientdb.wrapper.element.AbstractWrappedVertex;

public class JobImpl extends AbstractWrappedVertex implements Job {

	@Override
	public String getName() {
		return property("name");
	}

	@Override
	public void setName(String name) {
		property("name", name);
	}
}
