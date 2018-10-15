package com.gentics.diktyo.orientdb3;

import java.util.Iterator;

import org.apache.tinkerpop.gremlin.orientdb.OrientGraph;
import org.apache.tinkerpop.gremlin.orientdb.OrientGraphFactory;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.junit.Test;

import com.orientechnologies.orient.core.db.ODatabaseType;
import com.orientechnologies.orient.core.db.OrientDB;
import com.orientechnologies.orient.core.db.OrientDBConfig;

public class OrientDBUsageTest {

	@Test
	public void testOrientDB() {
		// Manager
		OrientDBConfig config = OrientDBConfig.defaultConfig();
		OrientDB orientDb = new OrientDB("embedded:./databases/", config);
		orientDb.create("test", ODatabaseType.MEMORY);

		// Tinkerpop 3.x API
		try (OrientGraphFactory graph = new OrientGraphFactory(orientDb, "test", ODatabaseType.MEMORY, "admin", "admin")) {
			graph.setupPool(10, 100);
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
