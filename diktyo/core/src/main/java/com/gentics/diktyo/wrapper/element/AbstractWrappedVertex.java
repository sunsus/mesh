package com.gentics.diktyo.wrapper.element;

public class AbstractWrappedVertex<T> implements WrappedVertex {

	private T delegate;

	public AbstractWrappedVertex(T delegate) {
		this.delegate = delegate;
	}

	@Override
	public <T> T getId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void remove() {
		// TODO Auto-generated method stub

	}

}
