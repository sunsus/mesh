package com.gentics.diktyo.orientdb;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.gentics.diktyo.Diktyo;
import com.gentics.diktyo.db.DatabaseManager;

@Singleton
public class DiktyoImpl implements Diktyo {

	private final DatabaseManager dbManager;

	@Inject
	public DiktyoImpl(DatabaseManager dbManager) {
		this.dbManager = dbManager;
	}

	@Override
	public DatabaseManager db() {
		return dbManager;
	}

}
