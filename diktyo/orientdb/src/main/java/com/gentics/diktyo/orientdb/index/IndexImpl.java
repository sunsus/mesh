package com.gentics.diktyo.orientdb.index;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.gentics.diktyo.index.AbstractIndex;

@Singleton
public class IndexImpl extends AbstractIndex {

	@Inject
	public IndexImpl() {
	}

	@Override
	public void list() {
		System.out.println("list");
	}
}
