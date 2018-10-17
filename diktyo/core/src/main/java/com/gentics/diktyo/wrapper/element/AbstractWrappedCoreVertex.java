package com.gentics.diktyo.wrapper.element;

public abstract class AbstractWrappedCoreVertex<V, E> implements WrappedVertex<V, E> {

	private V delegate;

	@Override
	public V delegate() {
		return delegate;
	}

	public void setDelegate(V delegate) {
		this.delegate = delegate;
	}

}
