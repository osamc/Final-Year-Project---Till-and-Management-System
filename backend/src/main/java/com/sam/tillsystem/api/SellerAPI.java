package com.sam.tillsystem.api;

import com.sam.tillsystem.models.user.Seller;

public interface SellerAPI {

	/**
	 * Creates a new {@link Seller}
	 * @param name the name for the Seller
	 * @param loginCode the login code for the Seller
	 * @return the created {@link Seller}
	 */
	public Seller createSeller(String name, String loginCode);
	
	/**
	 * Gets a seller from a given Id
	 * @param id the id of the Seller
	 * @return the {@link Seller} if found, else null
	 */
	public Seller getSeller(int id);
	
	/**
	 * Updates a {@link Seller}
	 * @param seller to be updated
	 * @return either the Seller was updated or not
	 */
	public boolean updateSeller(Seller seller);
	
	/**
	 * Deletes a given {@link Seller}
	 * @param seller the {@link Seller} to delete
	 * @return a success flag
	 */
	public boolean removeSeller(Seller seller);
	/**
	 * Delete a given Seller by it's Id
	 * @param id id of the seller
	 * @return a success flag
	 */
	public boolean removeSeller(int id);
	
	
}
