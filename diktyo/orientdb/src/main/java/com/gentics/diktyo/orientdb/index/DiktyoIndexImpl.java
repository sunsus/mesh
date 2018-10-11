package com.gentics.diktyo.orientdb.index;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.gentics.diktyo.index.impl.AbstractDiktyoIndex;

@Singleton
public class DiktyoIndexImpl extends AbstractDiktyoIndex {

	@Inject
	public DiktyoIndexImpl() {
	}

	@Override
	public void list() {
		System.out.println("list");
	}
}
