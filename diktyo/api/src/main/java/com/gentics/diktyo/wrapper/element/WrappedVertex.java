package com.gentics.diktyo.wrapper.element;

import com.gentics.diktyo.wrapper.traversal.WrappedTraversal;

public interface WrappedVertex<V, E> extends WrappedElement<V> {

	// traverse()
	
	WrappedTraversal<V> out(String label);

	WrappedTraversal<V> in(String label);

	WrappedTraversal<E> outE(String label);

	WrappedTraversal<E> inE(String label);

	void linkOut(WrappedVertex<V, E> v, String label);

	void linkIn(WrappedVertex<V, E> v, String label);

	void unlinkOut(WrappedVertex<V, E> v, String label);

	void unlinkIn(WrappedVertex<V, E> v, String label);

	void setLinkOut(WrappedVertex<V, E> v, String label);

	void setLinkIn(WrappedVertex<V, E> v, String label);

}
