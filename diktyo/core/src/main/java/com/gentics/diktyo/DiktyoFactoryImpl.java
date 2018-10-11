package com.gentics.diktyo;

import com.gentics.diktyo.impl.DiktyoImpl;

public class DiktyoFactoryImpl implements DiktyoFactory {

	private static Diktyo instance;

	@Override
	public Diktyo instance() {
		if (instance == null) {
			instance = new DiktyoImpl();
		}
		return instance;
	}

}
