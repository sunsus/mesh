package com.gentics.diktyo.orientdb3;

import org.junit.Test;

import com.gentics.diktyo.Diktyo;
import com.gentics.diktyo.db.Database;
import com.gentics.diktyo.index.Index;

/*
 * Test which covers the basic function of the OrientDB vendor implementation.
 */
public class BasicTest {

	@Test
	public void testBasics() {
		Diktyo diktyo = Diktyo.diktyo();
		diktyo.db().create("test");
		try (Database db = diktyo.db().open("test")) {
			for (Index index: db.index().list()) {
				System.out.println(index.name());
			}
		}
		Database db = diktyo.db().open("test");
		db.close();
		db = diktyo.db().open("test");
	}

}
