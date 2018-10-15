package com.gentics.diktyo.wrapper.element;

public interface WrappedVertex extends WrappedElement {

	// traverse()

	WrappedTraversal out(String label);

	WrappedTraversal in(String label);

	WrappedTraversal outE(String label);

	WrappedTraversal inE(String label);

	void linkOut(WrappedVertex v, String label);

	void linkIn(WrappedVertex v, String label);

	void unlinkOut(WrappedVertex v, String label);

	void unlinkIn(WrappedVertex v, String label);

	void setLinkOut(WrappedVertex v, String label);

	void setLinkIn(WrappedVertex v, String label);

}
