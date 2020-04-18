package com.sam.tillsystem.models.product;

import java.time.LocalDateTime;
import java.util.List;

import com.sam.tillsystem.models.user.Seller;

/**
 * The representation of a transaction, this contains multiple {@link TransactionRecord}s, a seller id and other useful information
 * @author Sam
 *
 */
public class Transaction {

	private int transactionId;
	private int sellerId;
	private LocalDateTime salesDate;
	
	private List<TransactionRecord> transactions;
	private Seller seller;
	
	public int getTransactionId() {
		return this.transactionId;
	}
	
	public Transaction setTransactionId(int id) {
		this.transactionId = id;
		return this;
	}
	
	public int getSellerId() {
		return this.sellerId;
	}
	
	public Transaction setSellerId(int id) {
		this.sellerId = id;
		return this;
	}
	
	public LocalDateTime getSalesDate() {
		return this.salesDate;
	}
	
	public Transaction setSalesDate(LocalDateTime time) {
		this.salesDate = time;
		return this;
	}
	
	public List<TransactionRecord> getTransactions() {
		return this.transactions;
	}
	
	public Transaction setTransactions(List<TransactionRecord> transactions) {
		this.transactions = transactions;
		return this;
	}
	
	public Seller getSeller() {
		return seller;
	}

	public Transaction setSeller(Seller seller) {
		this.seller = seller;
		return this;
	}
	
	
}
