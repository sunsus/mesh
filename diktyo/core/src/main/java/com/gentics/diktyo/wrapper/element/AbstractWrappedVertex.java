package com.gentics.diktyo.wrapper.element;

public abstract class AbstractWrappedVertex<T> implements WrappedVertex {

	private T delegate;

	public AbstractWrappedVertex(T delegate) {
		this.delegate = delegate;
	}

	public T getDelegate() {
		return delegate;
	}

}
