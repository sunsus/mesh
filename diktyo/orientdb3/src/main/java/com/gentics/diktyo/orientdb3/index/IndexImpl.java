package com.gentics.diktyo.orientdb3.index;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.gentics.diktyo.index.AbstractIndex;

@Singleton
public class IndexImpl extends AbstractIndex {

	private String name;

	@Inject
	public IndexImpl() {
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String name() {
		return name;
	}
}
