package com.gentics.diktyo.orientdb3.wrapper.element;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.tinkerpop.gremlin.structure.Edge;
import org.apache.tinkerpop.gremlin.structure.Property;
import org.apache.tinkerpop.gremlin.structure.Vertex;

import com.gentics.diktyo.wrapper.element.AbstractWrappedEdge;
import com.gentics.diktyo.wrapper.element.WrappedVertex;

public class WrappedEdgeImpl extends AbstractWrappedEdge<Edge> {

	public WrappedEdgeImpl(Edge edge) {
		super(edge);
	}

	@Override
	public String label() {
		return delegate().label();
	}

	@Override
	public WrappedVertex<Vertex> inV() {
		return new WrappedVertexImpl(delegate().inVertex());
	}

	@Override
	public WrappedVertex<Vertex> outV() {
		return new WrappedVertexImpl(delegate().outVertex());
	}

	@Override
	public Object id() {
		return delegate().id();
	}

	@Override
	public void remove() {
		delegate().remove();
	}

	@Override
	public Object property(String key) {
		return delegate().property(key);
	}

	@Override
	public <R> void property(final String key, final R value) {
		delegate().property(key, value);
	}

	@Override
	public void removeProperty(String key) {
		delegate().property(key).remove();
	}

	@Override
	public Set<String> properties() {
		Set<String> set = new HashSet<>();
		Iterator<Property<Object>> it = delegate().properties();
		while (it.hasNext()) {
			Property<Object> p = it.next();
			set.add(p.key());
		}
		return set;
	}

}
