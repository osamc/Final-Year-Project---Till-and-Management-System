package com.sam.tillsystem.models.requests;

import com.sam.tillsystem.models.page.PageInfo;
import com.sam.tillsystem.models.product.Product;

public class PageAdditionRequest {

	private Product product;
	private PageInfo page;
	private int x;
	private int y;
	
	
	public Product getProduct() {
		return product;
	}
	public PageAdditionRequest setProduct(Product product) {
		this.product = product;
		return this;
	}
	public PageInfo getPage() {
		return page;
	}
	public PageAdditionRequest setPage(PageInfo page) {
		this.page = page;
		return this;
	}
	
	public int getX() {
		return x;
	}
	public PageAdditionRequest setX(int x) {
		this.x = x;
		return this;
	}
	public int getY() {
		return y;
	}
	public PageAdditionRequest setY(int y) {
		this.y = y;
		return this;
	}
	
	
	
}
