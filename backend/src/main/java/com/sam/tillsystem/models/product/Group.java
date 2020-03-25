package com.sam.tillsystem.models.product;

import java.util.List;

public class Group {

	private int id;
	private String name;
	private boolean system = false;
	
	private List<Product> products;

	public Group() {}
	
	public int getId() {
		return id;
	}

	public Group setId(int id) {
		this.id = id;
		return this;
	}

	public String getName() {
		return name;
	}

	public Group setName(String name) {
		this.name = name;
		return this;
	}

	public List<Product> getProducts() {
		return products;
	}

	public Group setProducts(List<Product> products) {
		this.products = products;
		return this;
	}

	public boolean isSystem() {
		return system;
	}

	public Group setSystem(boolean system) {
		this.system = system;
		return this;
	}
	
	
	
}
