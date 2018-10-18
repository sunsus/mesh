package com.gentics.diktyo.orientdb3;

import org.apache.tinkerpop.gremlin.orientdb.OrientGraph;
import org.apache.tinkerpop.gremlin.orientdb.OrientGraphFactory;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.junit.Test;

public class OrientDBUsageTest {

	@Test
	public void testOrientDB() {

		try (OrientGraphFactory graph = new OrientGraphFactory()) {
			graph.setupPool(10, 100);

			try (OrientGraph tx = graph.getTx()) {
				for (int e = 0; e < 100_000; e++) {
					Vertex v = tx.addVertex("Person");
					v.property("name", "blab" + e);
					Vertex v2 = tx.addVertex("Job");
					v2.property("name", "blub" + e);
				}
				tx.commit();
			}

			try (OrientGraph tx = graph.getTx()) {
				time(() -> {
					System.out.println("Persons: " + tx.traversal().V().hasLabel("Person").count().next());
				});
			}
		}
	}
	// Configuration idxConfig = new BaseConfiguration();
	// idxConfig.setProperty("type", "UNIQUE");
	// idxConfig.setProperty("keytype", OType.STRING);
	// graph.getNoTx().createVertexIndex("name", "Job", idxConfig);
	// time(() -> {
	// System.out.println("Count: " + tx.traversal().V().has("name", "blub99").count().next());
	// });
	// time(() -> {
	// System.out.println("Jobs: " + tx.getRawDatabase().getMetadata().getSchema().getClass("Job").count());
	// System.out.println("Jobs: " + tx.traversal().V().has("@class", "Job").count().next());
	// });
	// time(() -> {
	// System.out.println("Persons: " + tx.traversal().V().has("@class", "Person").count().next());
	// });

	public void time(Runnable r) {
		long start = System.currentTimeMillis();
		r.run();
		long dur = System.currentTimeMillis() - start;
		System.out.println("Duration: " + dur);
	}
}
