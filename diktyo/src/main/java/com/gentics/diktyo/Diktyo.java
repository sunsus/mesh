package com.gentics.diktyo;

import com.gentics.diktyo.db.DiktyoDB;
import com.gentics.diktyo.impl.DiktyoImpl;

public interface Diktyo {

	static Diktyo diktyo() {
		return new DiktyoImpl();
	}

	/**
	 * Open the database with the given name.
	 * 
	 * @param name
	 */
	DiktyoDB open(String name);

	/**
	 * Create a new database with the given name.
	 * 
	 * @param name
	 */
	void create(String name);
}
