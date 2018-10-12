package com.gentics.diktyo.orientdb.index;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.gentics.diktyo.index.AbstractIndexManager;
import com.gentics.diktyo.index.Index;

@Singleton
public class IndexManagerImpl extends AbstractIndexManager {

	@Inject
	public IndexManagerImpl() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public boolean exists(String name) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Index get(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Index> list() {
		// TODO Auto-generated method stub
		return null;
	}

}
