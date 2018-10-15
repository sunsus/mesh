package com.gentics.diktyo.orientdb.wrapper.element;

import com.gentics.diktyo.wrapper.element.AbstractWrappedVertex;
import com.gentics.diktyo.wrapper.element.WrappedTraversal;
import com.tinkerpop.blueprints.Vertex;

public class WrappedVertex extends AbstractWrappedVertex<Vertex> {

	public WrappedVertex(Vertex vertex) {
		super(vertex);
	}

	@Override
	public WrappedTraversal out(String label) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WrappedTraversal in(String label) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WrappedTraversal outE(String label) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WrappedTraversal inE(String label) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void linkOut(com.gentics.diktyo.wrapper.element.WrappedVertex v, String label) {
		// TODO Auto-generated method stub

	}

	@Override
	public void linkIn(com.gentics.diktyo.wrapper.element.WrappedVertex v, String label) {
		// TODO Auto-generated method stub

	}

	@Override
	public void unlinkOut(com.gentics.diktyo.wrapper.element.WrappedVertex v, String label) {
		// TODO Auto-generated method stub

	}

	@Override
	public void unlinkIn(com.gentics.diktyo.wrapper.element.WrappedVertex v, String label) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setLinkOut(com.gentics.diktyo.wrapper.element.WrappedVertex v, String label) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setLinkIn(com.gentics.diktyo.wrapper.element.WrappedVertex v, String label) {
		// TODO Auto-generated method stub

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
