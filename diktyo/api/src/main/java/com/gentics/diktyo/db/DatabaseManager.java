package com.gentics.diktyo.db;

/**
 * Manager for graph databases.
 */
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

	/**
	 * Delete the database with the given name.
	 * 
	 * @param name
	 */
	void delete(String name);

	/**
	 * Check whether the given database exists.
	 * 
	 * @param name
	 * @return
	 */
	boolean exists(String name);

}
