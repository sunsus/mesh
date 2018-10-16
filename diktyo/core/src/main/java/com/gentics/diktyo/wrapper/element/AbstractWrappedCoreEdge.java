package com.gentics.diktyo.wrapper.element;

public abstract class AbstractWrappedCoreEdge<E,V> implements WrappedEdge<E, V> {

	private E delegate;

	public AbstractWrappedCoreEdge(E delegate) {
		this.delegate = delegate;
	}

	@Override
	public E delegate() {
		return delegate;
	}

	protected void setDelegate(E delegate) {
		this.delegate = delegate;
	}

}
