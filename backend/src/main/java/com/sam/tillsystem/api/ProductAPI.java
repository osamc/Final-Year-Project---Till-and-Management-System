package com.sam.tillsystem.api;

import java.util.List;

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
	
}
