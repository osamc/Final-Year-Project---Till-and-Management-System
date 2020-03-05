package com.sam.tillsystem.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sam.tillsystem.models.product.Product;
import com.sam.tillsystem.service.ProductImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/product")
@Tag(name = "Product API", description = "The description API for maintaining and creating products to be used within the system")
public class ProductController {

	@Autowired
	ProductImpl productService;

	@PostMapping("/createProduct")
	@Operation(summary = "Creates a product", description = "This method creates a product to be used within the system", responses = {
			@ApiResponse(responseCode = "200", description = "Product created"),
			@ApiResponse(responseCode = "400", description = "Product not created") })
	ResponseEntity<Product> createProduct(@RequestBody @Parameter(description = "Product to create") Product product) {
		Product created = this.productService.createProduct(product);
		return new ResponseEntity<>(created, created != null ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}
	
	@PostMapping("/updateProduct")
	@Operation(summary = "Updates a product", description = "This method Updates a product within the system", responses = {
			@ApiResponse(responseCode = "200", description = "Product Updates"),
			@ApiResponse(responseCode = "400", description = "Product not Updates") })
	ResponseEntity<Boolean> updateProduct(
			@RequestBody @Parameter(description = "Product to be updated") Product product) {
		Boolean success = this.productService.updateProduct(product);
		return new ResponseEntity<>(success, success ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}

	@PostMapping("/deleteProduct")
	@Operation(summary = "Deletes a product by Id", description = "Deletes the product associated with the given Id from the datbase", responses = {
			@ApiResponse(responseCode = "200", description = "Product deleted"),
			@ApiResponse(responseCode = "400", description = "Product not deleted") })
	ResponseEntity<Boolean> deleteProduct(
			@RequestBody @Parameter(description = "Product to be deleted") Product product) {
		Boolean success = this.productService.deleteProduct(product);
		return new ResponseEntity<>(success, success ? HttpStatus.OK : HttpStatus.BAD_REQUEST);

	}
	
	@GetMapping("/getProduct/{id}")
	@Operation(summary = "Get Product by Id", description = "Gets the product associated with the given id from the database", responses = {
			@ApiResponse(responseCode = "200", description = "Product Found"),
			@ApiResponse(responseCode = "400", description = "Product not found") })
	ResponseEntity<Product> getProduct(@PathVariable("id") @Parameter(description = "Id of product to find") int id) {
		Product product = this.productService.getProduct(id);
		return new ResponseEntity<>(product, product != null ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}
	
	@GetMapping("/getProducts")
	@Operation(summary = "Gets all Products", description = "Returns all products found within the database", responses = {
			@ApiResponse(responseCode = "200", description = "Product list returned")})
	ResponseEntity<List<Product>> getProducts() {
		return new ResponseEntity<>(this.productService.getProducts(), HttpStatus.OK);
	}
	

}
