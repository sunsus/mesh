package com.gentics.diktyo.orientdb3.wrapper.element;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.tinkerpop.gremlin.structure.Direction;
import org.apache.tinkerpop.gremlin.structure.Edge;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.apache.tinkerpop.gremlin.structure.VertexProperty;

import com.gentics.diktyo.orientdb3.wrapper.traversal.WrappedTraversalImpl;
import com.gentics.diktyo.wrapper.element.AbstractWrappedVertex;
import com.gentics.diktyo.wrapper.element.WrappedVertex;
import com.gentics.diktyo.wrapper.traversal.WrappedTraversal;

public class WrappedVertexImpl extends AbstractWrappedVertex<Vertex> {

	public WrappedVertexImpl(Vertex vertex) {
		super(vertex);
	}

	@Override
	public WrappedTraversal out(String label) {
		Iterator<Vertex> it = delegate().vertices(Direction.OUT, label);
		return new WrappedTraversalImpl(it);
	}

	@Override
	public WrappedTraversal in(String label) {
		Iterator<Vertex> it = delegate().vertices(Direction.IN, label);
		return new WrappedTraversalImpl(it);
	}

	@Override
	public WrappedTraversal outE(String label) {
		Iterator<Edge> it = delegate().edges(Direction.OUT, label);
		return new WrappedTraversalImpl(it);
	}

	@Override
	public WrappedTraversal inE(String label) {
		Iterator<Edge> it = delegate().edges(Direction.IN, label);
		return new WrappedTraversalImpl(it);
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
	public void linkOut(WrappedVertex<Vertex> v, String label) {
		delegate().addEdge(label, v.delegate());
	}

	@Override
	public void linkIn(WrappedVertex<Vertex> v, String label) {
		v.linkOut(this, label);
	}

	@Override
	public void unlinkOut(WrappedVertex<Vertex> v, String label) {
		delegate().edges(Direction.OUT, label);
	}

	@Override
	public void unlinkIn(WrappedVertex<Vertex> v, String label) {
		delegate().edges(Direction.IN, label);
	}

	@Override
	public void setLinkOut(WrappedVertex<Vertex> v, String label) {
		delegate().addEdge(label, v.delegate());
	}

	@Override
	public void setLinkIn(WrappedVertex<Vertex> v, String label) {
		v.delegate().addEdge(label, delegate());
	}

	@Override
	public Object property(String key) {
		VertexProperty<Object> p = delegate().property(key);
		return p.value();
	}

	@Override
	public <R> void property(String key, R value) {
		delegate().property(key, value);
	}

	@Override
	public void removeProperty(String key) {
		delegate().property(key).remove();
	}

	@Override
	public Set<String> properties() {
		Set<String> set = new HashSet<>();
		Iterator<VertexProperty<Object>> it = delegate().properties();
		while (it.hasNext()) {
			VertexProperty<Object> p = it.next();
			set.add(p.key());
		}
		return set;
	}

}
