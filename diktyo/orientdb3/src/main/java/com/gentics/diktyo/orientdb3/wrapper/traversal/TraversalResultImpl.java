package com.gentics.diktyo.orientdb3.wrapper.traversal;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import com.gentics.diktyo.orientdb3.wrapper.factory.WrapperFactory;
import com.gentics.diktyo.wrapper.element.WrappedElement;
import com.gentics.diktyo.wrapper.traversal.TraversalResult;

public class TraversalResultImpl<T> implements TraversalResult<T> {

	private final Iterator<T> it;

	public TraversalResultImpl(Iterator<T> it) {
		this.it = it;
	}



	@Override
	public <R extends WrappedElement<T>> Stream<R> stream(Class<R> clazzOfR) {
		Stream<T> stream = StreamSupport.stream(
			Spliterators.spliteratorUnknownSize(it, Spliterator.ORDERED),
			false);

		Stream<R> framedStream = stream.map(element -> WrapperFactory.frameElement(element, clazzOfR));
		return framedStream;
	}

	@Override
	public <R extends WrappedElement<T>> Iterable<R> iterable(Class<R> clazzOfR) {
		return () -> stream(clazzOfR).iterator();
	}

	@Override
	public <R extends WrappedElement<T>> Iterator<R> iterator(Class<R> clazzOfR) {
		return stream(clazzOfR).iterator();
	}

	@Override
	public <R extends WrappedElement<T>> R next(Class<R> clazzOfR) {
		if (it.hasNext()) {
			T element = it.next();
			return WrapperFactory.frameElement(element, clazzOfR);
		} else {
			return null;
		}
	}

}
