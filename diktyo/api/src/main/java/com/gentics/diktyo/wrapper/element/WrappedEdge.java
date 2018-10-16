package com.gentics.diktyo.wrapper.element;

public interface WrappedEdge<E,V> extends WrappedElement<E> {

	/**
	 * Return the label of the edge.
	 * 
	 * @return
	 */
	String label();

	/**
	 * Return the in bound vertex.
	 * 
	 * @param classOfR
	 * @return
	 */
	<R extends WrappedVertex<V>> R inV(Class<R> classOfR);

	/**
	 * Return the out bound vertex.
	 * 
	 * @param classOfR
	 * @return
	 */
	<R extends WrappedVertex<V>> R outV(Class<R> classOfR);

}
