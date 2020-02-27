package com.sam.tillsystem.api;

import java.util.List;

import com.sam.tillsystem.models.product.Product;

public interface ProductAPI {
	
	
	public Product createProduct(String name, String imageUrl, Double price);
	public Product createProduct(String name, String info, String imageUrl, Double price);
	public Product createProduct(Product product);
	
	public int updateProduct(Product product);
	
	public int deleteProduct(Product product);
	public int deleteProduct(int id);
	
	public Product getProduct(int id);
	public Product getProduct(Product product);
	
	public List<Product> getProducts();
	
}
