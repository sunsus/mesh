package com.gentics.diktyo.orientdb.wrapper.element;

import java.util.Set;

import com.gentics.diktyo.wrapper.element.AbstractWrappedEdge;
import com.gentics.diktyo.wrapper.element.WrappedVertex;
import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;

public class WrappedEdgeImpl extends AbstractWrappedEdge<Edge> {

	public WrappedEdgeImpl(Edge edge) {
		super(edge);
	}

	@Override
	public String label() {
		return delegate().getLabel();
	}

	@Override
	public WrappedVertex<Vertex> inV() {
		delegate().getVertex(Direction.IN);
		return null;
	}

	@Override
	public WrappedVertex<Vertex> outV() {
		delegate().getVertex(Direction.OUT);
		return null;
	}

	@Override
	public Object id() {
		return delegate().getId();
	}

	@Override
	public void remove() {
		delegate().remove();
	}

	@Override
	public Object property(String key) {
		return delegate().getProperty(key);
	}

	@Override
	public <R> void property(String key, R value) {
		delegate().setProperty(key, value);
	}

	@Override
	public void removeProperty(String key) {
		delegate().removeProperty(key);
	}

	@Override
	public Set<String> properties() {
		return delegate().getPropertyKeys();
	}

}
