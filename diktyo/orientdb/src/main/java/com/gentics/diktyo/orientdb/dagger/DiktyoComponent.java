package com.gentics.diktyo.orientdb.dagger;

import javax.inject.Singleton;

import com.gentics.diktyo.Diktyo;

import dagger.Component;

@Singleton
@Component(modules = { BindModule.class })
public interface DiktyoComponent {

	Diktyo diktyo();
}
