package com.gentics.diktyo.orientdb.db;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

import com.gentics.diktyo.db.Database;
import com.gentics.diktyo.db.DatabaseManager;

@Singleton
public class DatabaseManagerImpl implements DatabaseManager {

	private final Provider<Database> dbProvider;

	@Inject
	public DatabaseManagerImpl(Provider<Database> dbProvider) {
		this.dbProvider = dbProvider;
	}

	@Override
	public Database open(String name) {
		Database db = dbProvider.get();
		// TODO open factory inside db
		return db;
	}

	@Override
	public void create(String name) {
	}

	@Override
	public void delete(String name) {

	}
	
	@Override
	public boolean exists(String name) {
		// TODO Auto-generated method stub
		return false;
	}
}
