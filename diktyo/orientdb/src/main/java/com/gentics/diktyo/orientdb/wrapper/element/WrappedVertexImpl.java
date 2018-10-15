package com.gentics.diktyo.orientdb.wrapper.element;

import java.util.Set;

import com.gentics.diktyo.orientdb.wrapper.traversal.WrappedTraversalImpl;
import com.gentics.diktyo.wrapper.element.AbstractWrappedVertex;
import com.gentics.diktyo.wrapper.element.WrappedVertex;
import com.gentics.diktyo.wrapper.traversal.WrappedTraversal;
import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Vertex;

public class WrappedVertexImpl extends AbstractWrappedVertex<Vertex> {

	public WrappedVertexImpl(Vertex vertex) {
		super(vertex);
	}

	@Override
	public WrappedTraversal out(String label) {
		return new WrappedTraversalImpl(delegate().getVertices(Direction.OUT, label));
	}

	@Override
	public WrappedTraversal in(String label) {
		return new WrappedTraversalImpl(delegate().getVertices(Direction.IN, label));
	}

	@Override
	public WrappedTraversal outE(String label) {
		return new WrappedTraversalImpl(delegate().getEdges(Direction.OUT, label));
	}

	@Override
	public WrappedTraversal inE(String label) {
		return new WrappedTraversalImpl(delegate().getEdges(Direction.IN, label));
	}

	@Override
	public void linkOut(WrappedVertex<Vertex> v, String label) {
		delegate().addEdge(label, v.delegate());
	}

	@Override
	public void linkIn(WrappedVertex<Vertex> v, String label) {
		v.delegate().addEdge(label, delegate());
	}

	@Override
	public void unlinkOut(WrappedVertex<Vertex> v, String label) {
		delegate().getEdges(Direction.OUT, label);
	}

	@Override
	public void unlinkIn(WrappedVertex<Vertex> v, String label) {
		delegate().getEdges(Direction.IN, label);
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
