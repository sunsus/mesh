package com.gentics.diktyo.wrapper.element;

public abstract class AbstractWrappedCoreVertex<T> implements WrappedVertex<T> {

	private T delegate;

	@Override
	public T delegate() {
		return delegate;
	}

	public void setDelegate(T delegate) {
		this.delegate = delegate;
	}

}
