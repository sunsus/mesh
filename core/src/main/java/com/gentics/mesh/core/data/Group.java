package com.gentics.mesh.core.data;

import java.util.List;

import com.gentics.mesh.core.Page;
import com.gentics.mesh.core.rest.group.GroupReference;
import com.gentics.mesh.core.rest.group.GroupResponse;
import com.gentics.mesh.query.impl.PagingParameter;
import com.gentics.mesh.util.InvalidArgumentException;

public interface Group extends GenericVertex<GroupResponse>, NamedVertex, IndexedVertex, ReferenceableElement<GroupReference> {

	public static final String TYPE = "group";

	/**
	 * Assign the given user to this group
	 * 
	 * @param user
	 */
	void addUser(User user);

	/**
	 * Unassign the user from the group
	 * 
	 * @param user
	 */
	void removeUser(User user);

	/**
	 * Assign the given role to this group
	 * 
	 * @param role
	 */
	void addRole(Role role);

	/**
	 * Unassign the role from this group
	 * 
	 * @param role
	 */
	void removeRole(Role role);

	/**
	 * Return a list of users that are assigned to the group.
	 * 
	 * @return
	 */
	List<? extends User> getUsers();

	/**
	 * Return the a list of roles that are assigned to the group.
	 * 
	 * @return
	 */
	List<? extends Role> getRoles();

	/**
	 * Check whether the user has been assigned to the group.
	 * 
	 * @param user
	 * @return
	 */
	boolean hasUser(User user);

	/**
	 * Check whether the role has been assigned to the group.
	 * 
	 * @param role
	 * @return
	 */
	boolean hasRole(Role role);

	/**
	 * Return a page with all visible roles that the given user can see.
	 * 
	 * @param requestUser
	 * @param pagingInfo
	 * @return
	 * @throws InvalidArgumentException Request may fail when invalid paging parameters are provided
	 */
	Page<? extends Role> getRoles(MeshAuthUser requestUser, PagingParameter pagingInfo) throws InvalidArgumentException;

	/**
	 * Return a page with all users that the given user can see.
	 * 
	 * @param requestUser
	 * @param pagingInfo
	 * @return Page with found users, an empty page is returned when no users could be found
	 * @throws InvalidArgumentException Request may fail when invalid paging parameters are provided
	 */
	Page<? extends User> getVisibleUsers(MeshAuthUser requestUser, PagingParameter pagingInfo) throws InvalidArgumentException;

}
