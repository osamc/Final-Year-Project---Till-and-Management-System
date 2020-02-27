package com.sam.tillsystem.models.product;

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

		if (cast.getId() != cast.getId()) {
			return false;
		}

		if (cast.getName() != null && this.getName() != null) {
			if (!cast.getName().equals(this.getName())) {
				return false;
			}
		}

		if (cast.getImage() != null && this.getImage() != null) {
			if (!cast.getImage().equals(this.getImage())) {
				return false;
			}
		}

		if (cast.getInfo() != null && this.getInfo() != null) {
			if (!cast.getInfo().equals(this.getInfo())) {
				return false;
			}
		}

		if (cast.getPrice() != null && this.getPrice() != null) {
			if (!cast.getPrice().equals(this.getPrice())) {
				return false;
			}
		}

		return true;
	}

}
