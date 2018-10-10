package com.gentics.diktyo;

import org.junit.Test;

import com.gentics.diktyo.db.DiktyoDB;

public class DiktyoTest {

	@Test
	public void testBasicAPI() {
		Diktyo diktyo = Diktyo.diktyo();

		diktyo.create("db");
		DiktyoDB db = diktyo.open("db");
	}
}
