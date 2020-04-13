package com.sam.tillsystem.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.sam.tillsystem.api.TransactionAPI;
import com.sam.tillsystem.models.product.Transaction;
import com.sam.tillsystem.models.product.TransactionRecord;
import com.sam.tillsystem.models.user.Seller;

@Repository
public class TransactionImpl extends BaseImpl implements TransactionAPI {

	@Autowired
	ProductImpl productService;
	
	@Autowired
	SellerImpl sellerService;

	private RowMapper<Transaction> transactionMapper = new RowMapper<Transaction>() {

		@Override
		public Transaction mapRow(ResultSet rs, int rowNum) throws SQLException {
			Transaction trans = new Transaction();
			trans.setTransactionId(rs.getInt("transaction_id"));
			trans.setSellerId(rs.getInt("seller_id"));
			trans.setSalesDate(rs.getTimestamp("date").toLocalDateTime());
			
			Seller seller = sellerService.getSeller(trans.getSellerId());
			trans.setSeller(seller);

			List<TransactionRecord> list = template.query("SELECT * FROM transaction_details WHERE trans_id=?",
					new Object[] { trans.getTransactionId() }, new int[] { Types.INTEGER }, recordMapper);

			trans.setTransactions(list);

			return trans;
		}

	};

	private RowMapper<TransactionRecord> recordMapper = new RowMapper<TransactionRecord>() {

		@Override
		public TransactionRecord mapRow(ResultSet rs, int rowNum) throws SQLException {
			TransactionRecord record = new TransactionRecord();
			record.setProduct(productService.getProduct(rs.getInt("product_id")));
			record.setQuantity(rs.getInt("quantity"));
			record.setPrice(rs.getDouble("price"));
			return record;
		}

	};

	public TransactionImpl(JdbcTemplate template) {
		super(template);
	}

	private boolean createRecord(int transactionId, TransactionRecord record) {

		return this.template.update(
				"INSERT INTO transaction_details (trans_id, product_id, quantity, price) VALUES (?,?,?,?)",
				new Object[] { transactionId, record.getProduct().getId(), record.getQuantity(), record.getPrice() },
				new int[] { Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.DECIMAL }) > 0;

	}

	@Override
	public Transaction createTransaction(Transaction transaction) {

		this.template.update("INSERT INTO transaction_record (seller_id, date) VALUES (?,?)",
				new Object[] { transaction.getSellerId(), new Timestamp(new Date().getTime()) },
				new int[] { Types.INTEGER, Types.TIMESTAMP });

		Transaction fromDb = getNewestTransaction();

		transaction.getTransactions().stream().forEach(rec -> {
			createRecord(fromDb.getTransactionId(), rec);
		});

		return fromDb;
	}

	@Override
	public Transaction getTransaction(int id) {

		return this.template.query("SELECT * FROM transaction_record WHERE transaction_id=?", new Object[] { id },
				new int[] { Types.INTEGER }, transactionMapper).stream().findFirst().orElse(null);

	}

	@Override
	public boolean deleteTransaction(int id) {
		return this.template.update("DELETE FROM transaction_record WHERE transaction_id=?", new Object[] { id },
				new int[] { Types.INTEGER }) > 0;
	}

	@Override
	public Transaction getNewestTransaction() {
		return this.template.query("SELECT * FROM transaction_record ORDER BY transaction_id DESC LIMIT 1", transactionMapper).stream().findFirst()
		.orElse(null);
	}

	@Override
	public List<Transaction> getTransactions() {
		return this.template.query("SELECT * FROM transaction_record", transactionMapper);
	}

	@Override
	public List<Transaction> getTransactionsForUser(int id) {
		return this.template.query("SELECT * FROM transaction_record WHERE seller_id=?", new Object[] {id} , new int[] {Types.INTEGER} , transactionMapper);
	}

}
