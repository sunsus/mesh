package com.gentics.diktyo.wrapper.element;

import com.gentics.diktyo.index.IndexManager;

public abstract class AbstractWrappedCoreElement<E> implements WrappedElement<E> {

	private E delegate;

	@Override
	public E delegate() {
		return delegate;
	}

	protected void setDelegate(E delegate) {
		this.delegate = delegate;
	}

	@Override
	public IndexManager index() {
		// TODO Auto-generated method stub
		return null;
	}

}
