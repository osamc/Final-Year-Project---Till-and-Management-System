package com.sam.tillsystem.models.product;

/**
 * The representation of a transaction record. This is the product, quantity and cost contained into a single object.
 * @author Sam
 *
 */
public class TransactionRecord {

	private Product product;
	private int quantity;
	
	private double price;
	
	public Product getProduct() {
		return this.product;
	}
	
	public TransactionRecord setProduct(Product prod) {
		this.product = prod;
		return this;
	}
	
	public int getQuantity() {
		return this.quantity;
	}
	
	public TransactionRecord setQuantity(int quan) {
		this.quantity = quan;
		return this;
	}
	
	public double getPrice() {
		return this.price;
	}
	
	public TransactionRecord setPrice(double price) {
		this.price = price;
		return this;
	}
	
	
}
