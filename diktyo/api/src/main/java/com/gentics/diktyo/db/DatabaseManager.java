package com.gentics.diktyo.db;

public interface DatabaseManager {

	/**
	 * Open the database with the given name.
	 * 
	 * @param name
	 */
	Database open(String name);

	/**
	 * Create a new database with the given name.
	 * 
	 * @param name
	 */
	void create(String name);

}
