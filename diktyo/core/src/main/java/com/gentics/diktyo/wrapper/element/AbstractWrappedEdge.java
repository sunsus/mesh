package com.gentics.diktyo.wrapper.element;

public abstract class AbstractWrappedEdge<T> implements WrappedEdge {

	private T delegate;

	public AbstractWrappedEdge(T delegate) {
		this.delegate = delegate;
	}

	public T getDelegate() {
		return delegate;
	}

}
