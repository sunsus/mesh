package com.gentics.diktyo.orientdb.wrapper.factory;

import com.gentics.diktyo.orientdb.wrapper.element.AbstractWrappedEdge;
import com.gentics.diktyo.orientdb.wrapper.element.AbstractWrappedVertex;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Element;
import com.tinkerpop.blueprints.Vertex;

public final class WrapperFactory {

	/**
	 * Frame the given element by wrapping it using the given wrapper.
	 * 
	 * @param sourceElement
	 *            Element to be wrapped
	 * @param clazzOfR
	 *            Wrapper class
	 * @return Wrapped element
	 * 
	 */
	public static <R> R frameElement(Element element, Class<R> clazzOfR) {
		if (element == null) {
			return null;
		}
		if (element instanceof Vertex) {
			return frameVertex((Vertex) element, clazzOfR);
		}
		if (element instanceof Edge) {
			return frameEdge((Edge) element, clazzOfR);
		}
		throw new RuntimeException("Unknonwn type of element {" + element.getClass() + "}");
	}


	public static <R> R frameVertex(Vertex vertex, Class<R> classOfR) {
		try {
			R element = classOfR.newInstance();
			if (element instanceof AbstractWrappedVertex) {
				((AbstractWrappedVertex) element).init(vertex);
			} else {
				throw new RuntimeException("The specified class {" + classOfR + "} does not use {" + AbstractWrappedVertex.class + "}");
			}
			return element;
		} catch (InstantiationException | IllegalAccessException e) {
			throw new RuntimeException("Could not instantiate wrapper for class {" + classOfR.getName() + "}", e);
		}
	}
	
	public static <R> R frameEdge(Edge edge, Class<R> clazzOfR) {
		try {
			R element = clazzOfR.newInstance();
			if (element instanceof AbstractWrappedEdge) {
				((AbstractWrappedEdge) element).init(edge);
			} else {
				throw new RuntimeException("The specified class {" + clazzOfR + "} does not use {" + AbstractWrappedVertex.class + "}");
			}
			return element;
		} catch (InstantiationException | IllegalAccessException e) {
			throw new RuntimeException("Could not instantiate wrapper for class {" + clazzOfR.getName() + "}", e);
		}
	}
}
