package com.sam.tillsystem.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.sam.tillsystem.models.product.Product;
import com.sam.tillsystem.models.product.Transaction;
import com.sam.tillsystem.models.product.TransactionRecord;
import com.sam.tillsystem.models.user.Seller;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TransactionTests {
	
	@Autowired
	DataSource ds;
	
	@Autowired
	SellerImpl sellerService;
	
	@Autowired
	ProductImpl productService;
	
	@Autowired
	TransactionImpl transactionService;
	
	@Before
	//Make sure to clear the database before each test is called
	public void beforeTest() throws SQLException {
		
		try (Connection con = ds.getConnection()) {
			con.createStatement().execute("DELETE FROM transaction_record WHERE 1=1");
			con.createStatement().execute("ALTER TABLE transaction_record ALTER COLUMN transaction_id RESTART WITH 1");
			con.createStatement().execute("DELETE FROM transaction_details WHERE 1=1");
			con.createStatement().execute("DELETE FROM product WHERE 1=1");
			con.createStatement().execute("ALTER TABLE product ALTER COLUMN id RESTART WITH 1");
			con.createStatement().execute("DELETE FROM seller WHERE 1=1");
			con.createStatement().execute("ALTER TABLE seller ALTER COLUMN id RESTART WITH 1");
		}
		
	}
	
	private Transaction createTransaction() {
		Seller seller = this.sellerService.createSeller("test", "001");
		Product product = this.productService.createProduct("test", "google.com", 22.0);
		
		Transaction transaction = new Transaction();
		TransactionRecord record = new TransactionRecord();
		
		record.setProduct(product);
		record.setPrice(22.0);
		record.setQuantity(1);
		
		List<TransactionRecord> records = new ArrayList<TransactionRecord>();
		records.add(record);
		
		transaction.setTransactions(records);
		transaction.setSellerId(seller.getId());
		transaction.setSalesDate(LocalDateTime.now());
		
		return transaction;
	}
	
	@Test
	public void createTransactionTest() {
		
		Transaction fromDb = transactionService.createTransaction(createTransaction());
		
		assertNotNull(fromDb);
		assertTrue(fromDb.getSellerId() == 1);
	}
	
	@Test
	public void createTransactionWithMultipleProducts() {
		Product product = this.productService.createProduct("test2", "google.com", 11.0);
		Transaction transaction = createTransaction();
		TransactionRecord record = new TransactionRecord();
		record.setProduct(product).setPrice(11.0).setQuantity(1);
		transaction.getTransactions().add(record);
		
		Transaction fromDb = transactionService.createTransaction(transaction);
		
		assertNotNull(fromDb);
		assertTrue(fromDb.getSellerId() == 1);
	}
	
	@Test
	public void getTransaction() {
		Transaction initial = transactionService.createTransaction(createTransaction());
		Transaction fromDb = transactionService.getTransaction(initial.getTransactionId());
	
		assertNotNull(fromDb);
		assertTrue(fromDb.getSellerId() == initial.getSellerId());
		assertTrue(fromDb.getTransactionId() == initial.getTransactionId());
	}
	
	@Test
	public void deleteTransactionTest() {
		Transaction initial = transactionService.createTransaction(createTransaction());
		List<Transaction> before = transactionService.getTransactions();
		transactionService.deleteTransaction(initial.getTransactionId());
		List<Transaction> after = transactionService.getTransactions();	
		
		assertTrue(before.size() == 1);
		assertTrue(after.size() == 0);
	}
	
	@Test
	public void deleteSellerTest() {
		Transaction initial = transactionService.createTransaction(createTransaction());
		List<Transaction> before = transactionService.getTransactions();

		sellerService.removeSeller(initial.getSellerId());
		
		List<Transaction> after = transactionService.getTransactions();	
		
		assertTrue(before.size() == 1);
		assertTrue(after.size() == 0);
	}
	
	@Test
	public void testGetTransactionsForSeller() {
		
		List<Transaction> transaction = new ArrayList<Transaction>();
		
		for(int i = 0; i < 10; i ++) {
			Transaction created = createTransaction();
			for(int j = 0; j < 10; j++) {
				Transaction t = transactionService.createTransaction(created);
				transaction.add(t);
			}
		}
		
		int random = new Random().nextInt(10);
		
		List<Transaction> transactionList = transactionService.getTransactionsForUser(random);
		
		assertTrue(transactionList.size() == 10);
		assertTrue(transactionList.get(2).getSellerId() == random);
		
	}
	

}
