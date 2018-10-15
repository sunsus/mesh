package com.gentics.diktyo.wrapper.element;

public interface WrappedElement {

	// JsonObject toJson();

	/**
	 * @param <T>
	 *            The ID of the element.
	 * @return The id of this element.
	 */
	<T> T getId();

	/**
	 * Remove this element from the graph.
	 */
	void remove();

	// setProperty()
	// getProperty()
	// removeProperty()
}
