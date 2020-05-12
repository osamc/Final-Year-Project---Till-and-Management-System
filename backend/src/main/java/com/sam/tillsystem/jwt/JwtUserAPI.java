package com.sam.tillsystem.jwt;

/**
 * An API for accessing JWT User database entries
 * @author Sam
 *
 */
public interface JwtUserAPI {
	
	/**
	 * Locate a user by the username
	 * @param username the username to look up
	 * @return the found {@link JwtUser}
	 */
	public JwtUser findByUsername(String username);
	
	/**
	 * Saves the given user within the database
	 * @param user the {@link JwtUser} to save
	 * @return a flag representing if the save was successful
	 */
	public boolean saveUser(JwtUser user);
	
	/**
	 * Updates a user within the database
	 * @param user the {@link JwtUser} to update
	 * @return a flag representing if the update was successful
	 */
	public boolean updateUser(JwtUser user);
	
	/**
	 * Checks to see if this is the current users first login
	 * @return flag representing if this is the users first login
	 */
	public boolean getFirstLogin();
	
	/**
	 * Updates the logged in users password
	 * @param req the {@link PasswordRequest} request object
	 * @return flag representing if the request was succesful
	 */
	public boolean passwordUpdate(PasswordRequest req);

}
