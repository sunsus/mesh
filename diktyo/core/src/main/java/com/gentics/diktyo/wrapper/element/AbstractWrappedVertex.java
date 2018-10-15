package com.gentics.diktyo.wrapper.element;

public abstract class AbstractWrappedVertex<T> implements WrappedVertex<T> {

	private T delegate;

	public AbstractWrappedVertex(T delegate) {
		this.delegate = delegate;
	}

	@Override
	public T delegate() {
		return delegate;
	}

}
