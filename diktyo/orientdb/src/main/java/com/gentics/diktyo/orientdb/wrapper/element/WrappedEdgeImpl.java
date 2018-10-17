package com.gentics.diktyo.orientdb.wrapper.element;

import java.util.Set;

import com.gentics.diktyo.orientdb.wrapper.factory.WrapperFactory;
import com.gentics.diktyo.wrapper.element.AbstractWrappedCoreEdge;
import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;

public class WrappedEdgeImpl extends AbstractWrappedCoreEdge<Edge, Vertex> {

	@Override
	public void init(Edge element) {
		setDelegate(element);
	}

	@Override
	public String label() {
		return delegate().getLabel();
	}

	@Override
	public <R> R inV(Class<R> classOfR) {
		Vertex vertex = delegate().getVertex(Direction.IN);
		return WrapperFactory.frameVertex(vertex, classOfR);
	}

	@Override
	public <R> R outV(Class<R> classOfR) {
		Vertex vertex = delegate().getVertex(Direction.OUT);
		return WrapperFactory.frameVertex(vertex, classOfR);
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
	public <T> T property(String key) {
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
