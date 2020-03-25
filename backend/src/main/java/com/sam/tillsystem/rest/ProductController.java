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

import com.sam.tillsystem.models.product.Group;
import com.sam.tillsystem.models.product.Product;
import com.sam.tillsystem.service.ProductImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("product")
@Tag(name = "Product API", description = "The description API for maintaining and creating products to be used within the system")
public class ProductController {

	@Autowired
	ProductImpl productService;

	@PostMapping(value = "createProduct",consumes = "application/json" )
	@Operation(summary = "Creates a product", description = "This method creates a product to be used within the system", responses = {
			@ApiResponse(responseCode = "200", description = "Product created"),
			@ApiResponse(responseCode = "400", description = "Product not created") })
	ResponseEntity<Product> createProduct(@RequestBody @Parameter(description = "Product to create") Product product) {
		Product created = this.productService.createProduct(product);
		return new ResponseEntity<>(created, created != null ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}

	@PostMapping(value = "updateProduct", consumes = "application/json")
	@Operation(summary = "Updates a product", description = "This method Updates a product within the system", responses = {
			@ApiResponse(responseCode = "200", description = "Product Updated"),
			@ApiResponse(responseCode = "400", description = "Product not Updated") })
	ResponseEntity<Boolean> updateProduct(
			@RequestBody @Parameter(description = "Product to be updated") Product product) {
		Boolean success = this.productService.updateProduct(product);
		return new ResponseEntity<>(success, success ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}

	@PostMapping(value = "deleteProduct", consumes = "application/json")
	@Operation(summary = "Deletes a product by Id", description = "Deletes the product associated with the given Id from the datbase", responses = {
			@ApiResponse(responseCode = "200", description = "Product deleted"),
			@ApiResponse(responseCode = "400", description = "Product not deleted") })
	ResponseEntity<Boolean> deleteProduct(
			@RequestBody @Parameter(description = "Product to be deleted") Product product) {
		Boolean success = this.productService.deleteProduct(product);
		return new ResponseEntity<>(success, success ? HttpStatus.OK : HttpStatus.BAD_REQUEST);

	}

	@GetMapping(value = "getProduct/{id}", produces = "application/json")
	@Operation(summary = "Get Product by Id", description = "Gets the product associated with the given id from the database", responses = {
			@ApiResponse(responseCode = "200", description = "Product Found"),
			@ApiResponse(responseCode = "400", description = "Product not found") })
	ResponseEntity<Product> getProduct(@PathVariable("id") @Parameter(description = "Id of product to find") int id) {
		Product product = this.productService.getProduct(id);
		return new ResponseEntity<>(product, product != null ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}

	@GetMapping(value = "getProducts", produces = "application/json")
	@Operation(summary = "Gets all Products", description = "Returns all products found within the database", responses = {
			@ApiResponse(responseCode = "200", description = "Product list returned") })
	ResponseEntity<List<Product>> getProducts() {
		return new ResponseEntity<>(this.productService.getProducts(), HttpStatus.OK);
	}

	@PostMapping(value = "createGroup", consumes = "application/json")
	@Operation(summary = "Creates a new group for products", description = "Creates a new group that a product will be able to be associated with", responses = {
			@ApiResponse(responseCode = "200", description = "Product created"),
			@ApiResponse(responseCode = "400", description = "Product not created") })
	ResponseEntity<Group> createGroup(
			@RequestBody @Parameter(description = " The name of the group") String groupName) {
		Group group = this.productService.createGroup(groupName);
		return new ResponseEntity<>(group, group != null ? HttpStatus.OK : HttpStatus.BAD_REQUEST);

	}

	@PostMapping(value = "updateGroup", consumes = "application/json")
	@Operation(summary = "Updates a given group", description = "Updates the database entries for a given group. Currently the only thing to be "
			+ "updated this way is the name", responses = {
					@ApiResponse(responseCode = "200", description = "Group Updated"),
					@ApiResponse(responseCode = "400", description = "Group not Updated") })
	ResponseEntity<Boolean> updateGroup(@RequestBody @Parameter(description = "The group to be updated") Group group) {
		boolean success = this.productService.updateGroup(group);
		return new ResponseEntity<>(success, success ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}

	@PostMapping(value = "deleteGroup", consumes = "application/json")
	@Operation(summary = "Deletes a given group", description = "Removes the database entries for a given group.", responses = {
			@ApiResponse(responseCode = "200", description = "Group deleted"),
			@ApiResponse(responseCode = "400", description = "Group not deleted") })
	ResponseEntity<Boolean> deleteGroup(@RequestBody @Parameter(description = "The group to be deleted") Group group) {
		boolean success = this.productService.deleteGroup(group);
		return new ResponseEntity<>(success, success ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}

	@GetMapping(value = "getGroups", produces = "application/json")
	@Operation(summary = "Gets all groups from the database", description = "Gets all groups from within the database, depending on the flag sent, the groups may also contain all their products too.", responses = {
			@ApiResponse(responseCode = "200", description = "Group list returned") })
	ResponseEntity<List<Group>> getGroups(@Parameter(description = "Flag indicating if products also wanted") Boolean includeProducts) {
		return new ResponseEntity<>(this.productService.getGroups(includeProducts), HttpStatus.OK);
	}
	
	@GetMapping(value="getGroup/{id}", produces="application/json")
	@Operation(summary = "Gets a group from the database", description = "Gets a group from within the database, depending on the flag sent, the groups may also contain all their products too.", responses = {
			@ApiResponse(responseCode = "200", description = "Group returned") })
	ResponseEntity<Group> getGroup(@PathVariable("id") @Parameter(description = "Id of group to find") int id, @Parameter(description = "Flag indicating if products also wanted") Boolean includeProducts) {
		Group group = this.productService.getGroup(id, includeProducts);
		return new ResponseEntity<>(group, group != null ? HttpStatus.ACCEPTED : HttpStatus.BAD_REQUEST);
	}

}
