package com.gentics.diktyo.wrapper.element;

import java.util.Set;

public interface WrappedElement<T> {

	// JsonObject toJson();

	T delegate();

	/**
	 * The ID of the element.
	 * 
	 * @return The id of this element.
	 */
	Object id();

	/**
	 * Remove this element from the graph.
	 */
	void remove();

	/**
	 * Return the property with the given key.
	 * 
	 * @param key
	 * @return
	 */
	Object property(String key);

	/**
	 * Set the property.
	 * 
	 * @param key
	 * @param value
	 */
	<R> void property(String key, R value);

	/**
	 * Remove the property with the given key.
	 * 
	 * @param key
	 */
	void removeProperty(String key);

	/**
	 * Return a list of properties.
	 * 
	 * @return
	 */
	Set<String> properties();

	/**
	 * Initialize the wrapper.
	 * 
	 * @param element
	 */
	void init(T element);

}
