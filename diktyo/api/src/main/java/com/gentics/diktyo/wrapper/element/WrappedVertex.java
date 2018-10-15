package com.gentics.diktyo.wrapper.element;

import com.gentics.diktyo.wrapper.traversal.WrappedTraversal;

public interface WrappedVertex<V> extends WrappedElement<V> {

	// traverse()

	WrappedTraversal out(String label);

	WrappedTraversal in(String label);

	WrappedTraversal outE(String label);

	WrappedTraversal inE(String label);

	void linkOut(WrappedVertex<V> v, String label);

	void linkIn(WrappedVertex<V> v, String label);

	void unlinkOut(WrappedVertex<V> v, String label);

	void unlinkIn(WrappedVertex<V> v, String label);

	void setLinkOut(WrappedVertex<V> v, String label);

	void setLinkIn(WrappedVertex<V> v, String label);

}
