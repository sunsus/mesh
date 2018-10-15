package com.gentics.diktyo.wrapper.element;

public abstract class AbstractWrappedEdge<T> implements WrappedEdge<T> {

	private T delegate;

	public AbstractWrappedEdge(T delegate) {
		this.delegate = delegate;
	}

	@Override
	public T delegate() {
		return delegate;
	}

}
