package com.gentics.diktyo.orientdb3.dagger;

import com.gentics.diktyo.Diktyo;
import com.gentics.diktyo.index.Index;
import com.gentics.diktyo.orientdb3.DiktyoImpl;
import com.gentics.diktyo.orientdb3.index.IndexImpl;

import dagger.Binds;
import dagger.Module;

/**
 * Module which contains the bindings for the basic portal.
 */
@Module
public abstract class BindModule {

	@Binds
	public abstract Index bindIndex(IndexImpl index);

	@Binds
	public abstract Diktyo bindDikto(DiktyoImpl diktyo);
}
