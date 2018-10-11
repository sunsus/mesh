package com.gentics.diktyo.orientdb;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.gentics.diktyo.Diktyo;
import com.gentics.diktyo.db.DatabaseManager;
import com.gentics.diktyo.index.Index;
import com.gentics.diktyo.orientdb.db.DatabaseManagerImpl;
import com.gentics.diktyo.orientdb.index.IndexImpl;

@Singleton
public class DiktyoImpl implements Diktyo {

	@Inject
	public IndexImpl index;

	@Inject
	public DatabaseManagerImpl dbManager;

	@Inject
	public DiktyoImpl() {
	}

	@Override
	public DatabaseManager db() {
		return dbManager;
	}

	@Override
	public Index index() {
		return index;
	}

}
