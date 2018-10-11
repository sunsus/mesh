package com.gentics.diktyo.orientdb.db;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.gentics.diktyo.db.Database;
import com.gentics.diktyo.db.DatabaseManager;

@Singleton
public class DatabaseManagerImpl implements DatabaseManager {

	@Inject
	public DatabaseManagerImpl() {
	}

	@Override
	public Database open(String name) {
		return new DatabaseImpl();
	}

	@Override
	public void create(String name) {
	}
}
