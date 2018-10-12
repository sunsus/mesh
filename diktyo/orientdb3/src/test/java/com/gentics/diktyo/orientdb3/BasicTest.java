package com.gentics.diktyo.orientdb3;

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
		diktyo.db().create("test");
		Database db = diktyo.db().open("test");
		db.index().list();
		System.out.println(db);
		db.close();
		db = diktyo.db().open("test");
		System.out.println(db);
		db.close();
	}

}
