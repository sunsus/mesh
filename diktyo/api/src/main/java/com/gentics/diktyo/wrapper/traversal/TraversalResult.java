package com.gentics.diktyo.wrapper.traversal;

import java.util.Iterator;
import java.util.stream.Stream;

import com.gentics.diktyo.wrapper.element.WrappedElement;

public interface TraversalResult<T> {

	/**
	 * Return a stream of elements.
	 * 
	 * @param clazzOfR
	 * @return
	 */
	<R extends WrappedElement<T>> Stream<R> stream(Class<R> clazzOfR);

	/**
	 * Return an iterable of elements.
	 * 
	 * @param clazzOfR
	 * @return
	 */
	<R extends WrappedElement<T>> Iterable<R> iterable(Class<R> clazzOfR);

	/**
	 * Return an iterator of elements.
	 * 
	 * @param clazzOfR
	 * @return
	 */
	<R extends WrappedElement<T>> Iterator<R> iterator(Class<R> clazzOfR);

	/**
	 * Return the first found element.
	 * 
	 * @param clazzOfR
	 * @return
	 */
	<R extends WrappedElement<T>> R next(Class<R> clazzOfR);
}
