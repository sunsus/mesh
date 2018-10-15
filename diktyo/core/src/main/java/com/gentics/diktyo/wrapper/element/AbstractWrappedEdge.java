package com.gentics.diktyo.wrapper.element;

public class AbstractWrappedEdge<T> implements WrappedEdge {

	T edge;

	public AbstractWrappedEdge(T delegate) {
		this.edge = delegate;
	}

	@Override
	public <T> T getId() {
		return null;
	}

	@Override
	public void remove() {

	}

	@Override
	public String label() {
		return null;
	}

}
