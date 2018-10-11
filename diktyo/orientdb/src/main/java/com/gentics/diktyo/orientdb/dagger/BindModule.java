package com.gentics.diktyo.orientdb.dagger;

import com.gentics.diktyo.Diktyo;
import com.gentics.diktyo.index.Index;
import com.gentics.diktyo.orientdb.DiktyoImpl;
import com.gentics.diktyo.orientdb.index.DiktyoIndexImpl;

import dagger.Binds;
import dagger.Module;

/**
 * Module which contains the bindings for the basic portal.
 */
@Module
public abstract class BindModule {

	@Binds
	public abstract Index bindIndex(DiktyoIndexImpl index);

	@Binds
	public abstract Diktyo bindDikto(DiktyoImpl diktyo);
}
