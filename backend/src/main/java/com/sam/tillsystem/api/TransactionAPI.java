package com.sam.tillsystem.api;

import java.util.List;

import com.sam.tillsystem.models.product.Transaction;

/**
 * The transaction API is used for creating and maintaining transactions
 * @author Sam
 *
 */
public interface TransactionAPI {

	/**
	 * Creates a new {@link Transaction}
	 * @param transaction the {@link Transaction} to create
	 * @return the created {@link Transaction}
	 */
	public Transaction createTransaction(Transaction transaction);
	
	/**
	 * Gets a {@link Transaction} by it's ID
	 * @param id the id of the {@link Transaction} to find
	 * @return the found {@link Transaction}
	 */
	public Transaction getTransaction(int id);
	
	/**
	 * Deletes a transaction by it's id
	 * @param id id of the transaction
	 * @return a success flag
	 */
	public boolean deleteTransaction(int id);
	
	/**
	 * Gets all transactions
	 * @return a {@link List} of {@link Transaction}s
	 */
	public List<Transaction> getTransactions();
	
	/**
	 * Get all transactions that are associated with the given user id
	 * @param id the user id to find
	 * @return a {@link List} of {@link Transaction}s associated with the user
	 */
	public List<Transaction> getTransactionsForUser(int id);

	/**
	 * Gets the newest transaction to be displayed
	 * @return the newest {@link Transaction}
	 */
	public Transaction getNewestTransaction();
	
	
	
}
