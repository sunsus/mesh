package com.gentics.diktyo.orientdb;

import static com.gentics.diktyo.db.DatabaseType.MEMORY;

import org.junit.Test;

import com.gentics.diktyo.Diktyo;
import com.gentics.diktyo.db.Database;

/*
 * Test which covers the basic function of the OrientDB vendor implementation.
 */
public class BasicTest {

	@Test
	public void testBasics() {
		Diktyo diktyo = Diktyo.diktyo();
		diktyo.db().create("test", MEMORY);
		Database db = diktyo.db().open("test", MEMORY);
		db.index().list();
		db.close();
	}

}
