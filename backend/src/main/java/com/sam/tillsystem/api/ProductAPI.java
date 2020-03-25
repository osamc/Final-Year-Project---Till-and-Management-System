package com.sam.tillsystem.api;

import java.util.List;

import com.sam.tillsystem.models.product.Group;
import com.sam.tillsystem.models.product.Product;

public interface ProductAPI {
	
	/**
	 * Creates a new {@link Product}
	 * @param name The name of the product
	 * @param imageUrl The image to be used for the product
	 * @param price The price of the product
	 * @return the created {@link Product}
	 */
	public Product createProduct(String name, String imageUrl, Double price);
	
	/**
	 * Creates a new {@link Product}
	 * @param name The name of the product
	 * @param info The info tied to the product
	 * @param imageUrl The image to be used for the product
	 * @param price The price of the product
	 * @return the created {@link Product}
	 */
	public Product createProduct(String name, String info, String imageUrl, Double price);
	
	/**
	 * Creates a new {@link Product}
	 * @param name The name of the product
	 * @param info The info tied to the product
	 * @param imageUrl The image to be used for the product
	 * @param price The price of the product
	 * @param group The id of the associated group
	 * @return the created {@link Product}
	 */
	public Product createProduct(String name, String info, String imageUrl, Double price, Integer group);
	/**
	 * Creates a new {@link Product}
	 * @param product the {@link Product} to create
	 * @return the created {@link Product}
	 */
	public Product createProduct(Product product);
	
	/**
	 * Updates a {@link Product}
	 * @param product The {@link Product} to update
	 * @return a success flag
	 */
	public boolean updateProduct(Product product);
	
	/**
	 * Deletes a given {@link Product}
	 * @param product {@link Product} 
 	 * @return a success flag
	 */
	public boolean deleteProduct(Product product);
	/**
	 * Deletes a given product from its id
	 * @param id the id of the {@link Product} to delete
	 * @return a success flag
	 */
	public boolean deleteProduct(int id);
	
	/**
	 * Gets a {@link Product} from a given ID
	 * @param id id of product to find
	 * @return the found {@link Product}
	 */
	public Product getProduct(int id);
	/**
	 * Gets a {@link Product} from a given {@link Product}
	 * @param product to find
	 * @return the found {@link Product}
	 */
	public Product getProduct(Product product);
	
	/**
	 * Gets a {@link List} of {@link Product}s
	 * @return the list of all {@link Product}s
	 */
	public List<Product> getProducts();
	
	
	/**
	 * Creates a group that can be assigned to a product
	 * @param groupName the name of the group
	 * @return
	 */
	public Group createGroup(String groupName);
	
	/**
	 * Updates a group definition within the database
	 * @param group to be updated
	 * @return a success flag
	 */
	public boolean updateGroup(Group group);
	
	/**
	 * Deletes a group definition within the database
	 * @param group the group to be deleted
	 * @return a success flag
	 */
	public boolean deleteGroup(Group group);
	
	/**
	 * Get all groups
	 * @return a {@link List} list of {@link Group}s
	 */
	public List<Group> getGroups();
	
	/**
	 * Get all groups
	 * @param withProducts if flag is true, then all products will for said groups will be returned too
	 * @return bn  {@link List} list of {@link Group}s
	 */
	public List<Group> getGroups(boolean withProducts);

	/**
	 * Gets a specific group
	 * @param id the id of the group to be found
	 * @param withProducts a flag indicating with the products are required with the group
	 * @return a {@link Group}
	 */
	Group getGroup(int id, boolean withProducts);

	
	
}
