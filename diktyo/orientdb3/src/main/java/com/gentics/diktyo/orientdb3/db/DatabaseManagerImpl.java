package com.gentics.diktyo.orientdb3.db;

import java.util.Iterator;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.apache.tinkerpop.gremlin.orientdb.OrientGraph;
import org.apache.tinkerpop.gremlin.orientdb.OrientGraphFactory;
import org.apache.tinkerpop.gremlin.structure.Vertex;

import com.gentics.diktyo.db.Database;
import com.gentics.diktyo.db.DatabaseManager;
import com.orientechnologies.orient.core.db.ODatabasePool;
import com.orientechnologies.orient.core.db.ODatabaseType;
import com.orientechnologies.orient.core.db.OrientDB;
import com.orientechnologies.orient.core.db.OrientDBConfig;
import com.orientechnologies.orient.core.db.document.ODatabaseDocument;
import com.orientechnologies.orient.core.record.OVertex;

@Singleton
public class DatabaseManagerImpl implements DatabaseManager {

	@Inject
	public DatabaseManagerImpl() {

	}

	@Override
	public Database open(String name) {
		return new DatabaseImpl();
	}

	@Override
	public void create(String name) {
		// https://github.com/orientechnologies/orientdb-gremlin/blob/master/driver/src/test/java/org/apache/tinkerpop/gremlin/orientdb/OrientGraphTest.java
		try (OrientGraphFactory factory = new OrientGraphFactory("plocal:" + name)) {
		}
		// vs.
		OrientDB orientDb = new OrientDB("embedded:./databases/", OrientDBConfig.defaultConfig());
		orientDb.create("test", ODatabaseType.MEMORY);

		// Core API
		try (ODatabasePool pool = new ODatabasePool(orientDb, "test", "admin", "admin")) {
			try (ODatabaseDocument db = pool.acquire()) {
				OVertex v = db.newVertex();
				v.setProperty("test", "123");
				db.commit();
			}
		}

		// Tinkerpop 3.x API
		try (OrientGraphFactory graph = new OrientGraphFactory(orientDb, "test", ODatabaseType.MEMORY, "admin", "admin")) {
			try (OrientGraph tx = graph.getTx()) {
				Vertex v = tx.addVertex();
				v.property("test", "blub");
				Iterator<Vertex> it = tx.vertices();
				while (it.hasNext()) {
					System.out.println("Found vertex: " + it.next());
				}
			}
		}
	}
}
