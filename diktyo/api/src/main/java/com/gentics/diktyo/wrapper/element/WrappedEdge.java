package com.gentics.diktyo.wrapper.element;

public interface WrappedEdge<E> extends WrappedElement<E> {

	/**
	 * Return the label of the edge.
	 * 
	 * @return
	 */
	String label();

	/**
	 * Return the in bound vertex.
	 * @return
	 */
	<V> WrappedVertex<V> inV();

	/**
	 * Return the out bound vertex.
	 * @return
	 */
	<V> WrappedVertex<V> outV();

}
