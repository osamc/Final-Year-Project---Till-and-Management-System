package com.sam.tillsystem.models.Product;

public class Product {
	
	private int id;
	private String name;
	private String info;
	private String imageUrl;
	
	private Double price;
	
	public Product() {
		
	}
	
	public Product(String name, String image, Double price) {
		this.name = name;
		this.imageUrl = image;
		this.price = price;
	}
	
	public int getId() {
		return this.id;
	}
	
	public Product setId(int id) {
		this.id = id;
		return this;
	}
	
	public String getName() {
		return this.name;
	}
	
	public Product setName(String name) {
		this.name = name;
		return this;
	}
	
	public String getInfo() {
		return this.info;
	}
	
	public Product setInfo(String info) {
		this.info = info;
		return this;
	}
	
	public String getImage() {
		return this.imageUrl;
	}
	
	public Product setImage(String image) {
		this.imageUrl = image;
		return this;
	}
	
	public Double getPrice() {
		return this.price;
	}
	
	public Product setPrice(Double price) {
		this.price = price;
		return this;
	}

	@Override
	public boolean equals(Object toCompare) {
		
		Product cast = (Product) toCompare;
		
		return true;
	}
	
	
	
	
}
