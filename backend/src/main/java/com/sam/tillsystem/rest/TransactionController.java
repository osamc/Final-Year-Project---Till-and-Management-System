package com.sam.tillsystem.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sam.tillsystem.models.product.Transaction;
import com.sam.tillsystem.service.TransactionImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("transaction")
@Tag(name = "Transaction API", description = "The transaction API for dealing with transactions")
public class TransactionController {

	@Autowired
	TransactionImpl transactionService;

	@PostMapping(value = "createTransaction", consumes = "application/json")
	@Operation(summary = "Creates a Transaction", description = "This method creates a Transaction within the system", responses = {
			@ApiResponse(responseCode = "200", description = "Transaction created"),
			@ApiResponse(responseCode = "400", description = "Transaction not created") }, security = @SecurityRequirement(name = "bearerAuth"))
	ResponseEntity<Transaction> createTransaction(
			@RequestBody @Parameter(description = "Transaction to create") Transaction transaction) {
		Transaction created = this.transactionService.createTransaction(transaction);
		return new ResponseEntity<>(created, created != null ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}

	@GetMapping(value = "getTransaction/{id}", produces = "application/json")
	@Operation(summary = "Get Transaction by Id", description = "Gets the transaction associated with the given id from the database", responses = {
			@ApiResponse(responseCode = "200", description = "Transaction Found"),
			@ApiResponse(responseCode = "400", description = "Transaction not found") }, security = @SecurityRequirement(name = "bearerAuth"))
	ResponseEntity<Transaction> getTransaction(
			@PathVariable("id") @Parameter(description = "Id of transaction to find") int id) {
		Transaction fromDb = this.transactionService.getTransaction(id);
		return new ResponseEntity<>(fromDb, fromDb != null ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}

	@DeleteMapping(value = "deleteTransaction/{id}", consumes = "application/json")
	@Operation(summary = "Deletes a transaction by Id", description = "Deletes the transaction associated with the given Id from the datbase", responses = {
			@ApiResponse(responseCode = "200", description = "Transaction deleted"),
			@ApiResponse(responseCode = "400", description = "Transaction not deleted") }, security = @SecurityRequirement(name = "bearerAuth"))
	ResponseEntity<Boolean> deleteTransaction(
			@PathVariable("id") @Parameter(description = "Transaction to be deleted") int id) {
		Boolean success = this.transactionService.deleteTransaction(id);
		return new ResponseEntity<>(success, success ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}

	@GetMapping(value = "getTransactions", produces = "application/json")
	@Operation(summary = "Gets all Transactions", description = "Returns all Transactions found within the database", responses = {
			@ApiResponse(responseCode = "200", description = "Transaction list returned") }, security = @SecurityRequirement(name = "bearerAuth"))
	ResponseEntity<List<Transaction>> getTransactions() {
		return new ResponseEntity<>(this.transactionService.getTransactions(), HttpStatus.OK);
	}
	
	@GetMapping(value = "sellerTransaction/{id}", produces = "application/json")
	@Operation(summary = "Get transactions associated with a seller", description = "Returns a list of transactions associated with a given seller", responses = {
			@ApiResponse(responseCode = "200", description = "Transaction list returned") }, security = @SecurityRequirement(name = "bearerAuth"))
	ResponseEntity<List<Transaction>> getSellerTransactions(@PathVariable("id") @Parameter(description = "Id of seller to find transactions for") int id) {
		return new ResponseEntity<>(this.transactionService.getTransactionsForUser(id), HttpStatus.OK);
	} 
	
	@GetMapping(value = "newestTrans", produces = "application/json") 
	@Operation(summary = "Get the newest transaction", description = "Returns the newest transaction within the database to be shown within the transaction display component", responses = {
			@ApiResponse(responseCode = "200", description = "Newest transaction returned") }, security = @SecurityRequirement(name = "bearerAuth"))
	ResponseEntity<Transaction> getNewest() {
		return new ResponseEntity<>(this.transactionService.getNewestTransaction(), HttpStatus.OK);
	}
	
	@GetMapping(value = "transPage/{size}/{offset}", produces = "application/json")
	@Operation(summary = "Get a transaction page", 
	description = "Returns a slice of transactions from the database of a given safe and an offset", 
	responses = { @ApiResponse(responseCode = "200", description = "Transaction page obtained")}, security = @SecurityRequirement(name = "bearerAuth"))
	ResponseEntity<List<Transaction>> getTransactionPage(@PathVariable("size") int size, @PathVariable("offset") int offset) {
		return new ResponseEntity<>(this.transactionService.getTransactionPage(size, offset), HttpStatus.OK);
	}
	

}
