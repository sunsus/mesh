package com.gentics.diktyo.orientdb.domain;

import com.gentics.diktyo.wrapper.element.WrappedVertex;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;

public interface Job extends WrappedVertex<Vertex, Edge> {

	/**
	 * Return the job name.
	 * 
	 * @return
	 */
	String getName();

	/**
	 * Set the job name.
	 * 
	 * @param name
	 */
	void setName(String name);

}
