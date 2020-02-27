package com.sam.tillsystem.models.page;

import com.sam.tillsystem.models.product.Product;

public class PageDefinition {
	
	private int x;
	private int y;
	private Product product;
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public PageDefinition setX(int x) {
		this.x = x;
		return this;
	}
	
	public PageDefinition setY(int y) {
		this.y = y;
		return this;
	}
	
	public Product getProduct() {
		return this.product;
	}
	
	public PageDefinition setProduct(Product product) {
		this.product = product;
		return this;
	}
	
}
