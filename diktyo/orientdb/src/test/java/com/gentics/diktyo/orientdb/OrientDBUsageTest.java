package com.gentics.diktyo.orientdb;

import java.util.Iterator;

import org.junit.Test;

import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.orient.OrientGraph;
import com.tinkerpop.blueprints.impls.orient.OrientGraphFactory;

/**
 * Test Tinkerpop 2.6 Blueprint API
 */
public class OrientDBUsageTest {

	@Test
	public void testOrientDB() {

		OrientGraphFactory factory = new OrientGraphFactory("memory:tinkerpop").setupPool(16, 100);
		OrientGraph tx = factory.getTx();

		Vertex v = tx.addVertex(null);
		v.setProperty("test", "blub");
		Iterator<Vertex> it = tx.getVertices().iterator();
		while (it.hasNext()) {
			System.out.println("Found vertex: " + it.next());
		}

	}
}
