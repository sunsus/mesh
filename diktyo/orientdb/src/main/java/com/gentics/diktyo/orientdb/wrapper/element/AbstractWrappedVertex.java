package com.gentics.diktyo.orientdb.wrapper.element;

import java.util.Set;

import com.gentics.diktyo.orientdb.wrapper.traversal.WrappedTraversalImpl;
import com.gentics.diktyo.wrapper.element.AbstractWrappedCoreVertex;
import com.gentics.diktyo.wrapper.element.WrappedVertex;
import com.gentics.diktyo.wrapper.traversal.WrappedTraversal;
import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;

public abstract class AbstractWrappedVertex extends AbstractWrappedCoreVertex<Vertex, Edge> {

	@Override
	public void init(Vertex element) {
		setDelegate(element);
	}

	@Override
	public WrappedTraversal<Vertex> out(String label) {
		return new WrappedTraversalImpl<Vertex>(delegate().getVertices(Direction.OUT, label).iterator());
	}

	@Override
	public WrappedTraversal<Vertex> in(String label) {
		return new WrappedTraversalImpl<Vertex>(delegate().getVertices(Direction.IN, label).iterator());
	}

	@Override
	public WrappedTraversal<Edge> outE(String label) {
		return new WrappedTraversalImpl<Edge>(delegate().getEdges(Direction.OUT, label).iterator());
	}

	@Override
	public WrappedTraversal<Edge> inE(String label) {
		return new WrappedTraversalImpl<Edge>(delegate().getEdges(Direction.IN, label).iterator());
	}

	@Override
	public void linkOut(WrappedVertex<Vertex, Edge> v, String label) {
		delegate().addEdge(label, v.delegate());
	}

	@Override
	public void linkIn(WrappedVertex<Vertex, Edge> v, String label) {
		v.delegate().addEdge(label, delegate());
	}

	@Override
	public void unlinkOut(WrappedVertex<Vertex, Edge> v, String label) {
		delegate().getEdges(Direction.OUT, label);
	}

	@Override
	public void unlinkIn(WrappedVertex<Vertex, Edge> v, String label) {
		delegate().getEdges(Direction.IN, label);
	}

	@Override
	public void setLinkOut(WrappedVertex<Vertex, Edge> v, String label) {
		delegate().addEdge(label, v.delegate());
	}

	@Override
	public void setLinkIn(WrappedVertex<Vertex, Edge> v, String label) {
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
