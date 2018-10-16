package com.gentics.diktyo.orientdb3.wrapper.traversal;

import java.util.Iterator;

import com.gentics.diktyo.wrapper.traversal.AbstractWrappedTraversal;

public class WrappedTraversalImpl<T> extends AbstractWrappedTraversal {

	private Iterator<T> it;

	public WrappedTraversalImpl(Iterator<T> it) {
		this.it = it;
	}

}
