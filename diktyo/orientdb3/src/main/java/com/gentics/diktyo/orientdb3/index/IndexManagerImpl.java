package com.gentics.diktyo.orientdb3.index;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.apache.tinkerpop.gremlin.orientdb.OrientGraphFactory;

import com.gentics.diktyo.index.impl.AbstractIndexManager;
import com.orientechnologies.orient.core.db.ODatabaseSession;
import com.orientechnologies.orient.core.db.ODatabaseType;

@Singleton
public class IndexManagerImpl extends AbstractIndexManager {
	
	@Inject
	public IndexManagerImpl() {
	}
	
	@Override
	public boolean exists(String name) {
		ODatabaseSession db = null;
		db.getMetadata().getSchema().existsClass(name);
		
		return true;
	}

}
