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

import com.sam.tillesystem.exceptions.CoordinatesOutOfRangeException;
import com.sam.tillesystem.exceptions.PageInfoNotFoundException;
import com.sam.tillesystem.exceptions.ProductAlreadyExistsException;
import com.sam.tillesystem.exceptions.ProductNotFoundException;
import com.sam.tillsystem.models.page.PageInfo;
import com.sam.tillsystem.models.requests.PageAdditionRequest;
import com.sam.tillsystem.service.PageDefinitionImpl;
import com.sam.tillsystem.service.PageImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/page")
@Tag(name = "Page API", description = "The Page API is used for creating and maintaining pages within the Application")
public class PageController {

	@Autowired
	PageImpl pageService;

	@Autowired
	PageDefinitionImpl pageDefService;

	@PostMapping("/createPage")
	@Operation(summary = "Creates a page", description = "This method creates a page to be used within the system.", responses = {
			@ApiResponse(responseCode = "200", description = "Page created"),
			@ApiResponse(responseCode = "400", description = "page not created")})
	ResponseEntity<PageInfo> createPage(@RequestBody @Parameter(description = "The page to be created") PageInfo page) {
		PageInfo created = this.pageService.createPage(page);
		return new ResponseEntity<>(created, created != null ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}

	@GetMapping("/getPage/{id}")
	@Operation(summary = "Gets a page", description = "Gets a page from a given ID.", responses = {
			@ApiResponse(responseCode = "200", description = "Page found"),
			@ApiResponse(responseCode = "404", description = "No page found for given id") })
	ResponseEntity<PageInfo> getPage(@PathVariable("id") @Parameter(description = "The ID of the page") int id) {
		PageInfo page = this.pageService.getPage(id);
		return new ResponseEntity<>(page, page != null ? HttpStatus.OK : HttpStatus.NOT_FOUND);
	}

	@GetMapping("/getPages")
	@Operation(summary = "Gets a list of all pages", description = "Gets all pages from db.")
	ResponseEntity<List<PageInfo>> getPages() {
		return new ResponseEntity<>(this.pageService.getPages(), HttpStatus.OK);
	}

	@PostMapping("/updatePage")
	@Operation(summary = "Updates a page", description = "Updates a page, uses the id the found"
			+ " within the object to update that record within the database", responses = {
					@ApiResponse(responseCode = "200", description = "Page found and updated"),
					@ApiResponse(responseCode = "404", description = "No page found for given id") })
	ResponseEntity<Boolean> updatePage(
			@RequestBody @Parameter(description = "The page to be updated") PageInfo toUpdate) {
		Boolean success = this.pageService.updatePage(toUpdate) > 0;
		return new ResponseEntity<Boolean>(success, success ? HttpStatus.OK : HttpStatus.NOT_FOUND);
	}

	@DeleteMapping("/deletePage/{id}")
	@Operation(summary = "Deletes a page", description = "Deletes a page that is associated to a given id", responses = {
			@ApiResponse(responseCode = "200", description = "Page found and deleted"),
			@ApiResponse(responseCode = "404", description = "No page found for given id") })
	ResponseEntity<Boolean> deletePage(
			@PathVariable("id") @Parameter(description = "The id of the page to delete") int id) {
		Boolean deleted = this.pageService.deletePage(id);
		return new ResponseEntity<Boolean>(deleted, deleted ? HttpStatus.OK : HttpStatus.NOT_FOUND);
	}

	@PostMapping("/addItemToPage")
	@Operation(summary = "Adds a product to a page", description = "Adds a product to a given page at the coordinates (x,y)", responses = {
			@ApiResponse(responseCode = "200", description = "Item succesfully added to page"),
			@ApiResponse(responseCode = "400", description = "Item was not added to page"),
			@ApiResponse(responseCode = "500", description = "An error occured, the response should contain more info") })
	ResponseEntity<Boolean> addItemToPage(
			@RequestBody @Parameter(description = "The request for adding the product to the page") PageAdditionRequest request)
			throws PageInfoNotFoundException, ProductNotFoundException, CoordinatesOutOfRangeException,
			ProductAlreadyExistsException {
		Boolean success = false;
		success = this.pageDefService.addItemToPage(request.getProduct(), request.getPage(), request.getX(),
				request.getY());
		return new ResponseEntity<Boolean>(success, success ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}

	@PostMapping("/removeItemFromPage/{x}/{y}")
	@Operation(summary = "Removes an item from a Page", description = "Removes the product on a given page at the coordinates (x,y)", responses = {
			@ApiResponse(responseCode = "200", description = "Item succesfully removed from the page"),
			@ApiResponse(responseCode = "400", description = "Item was not removed to page"),
			@ApiResponse(responseCode = "500", description = "An error occured, the response should contain more info") })
	ResponseEntity<Boolean> removeItemFromPage(
			@PathVariable("x") @Parameter(description = "X coordinate of the Page") int x,
			@PathVariable("y") @Parameter(description = "Y coordinate of the Page") int y,
			@RequestBody @Parameter(description = "The Page to have the product removed from") PageInfo page)
			throws ProductNotFoundException {
		Boolean success = this.pageDefService.removeItemFromPage(page, x, y);
		return new ResponseEntity<Boolean>(success, success ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}

	@PostMapping("/clearPage")
	@Operation(summary = "Removes all items from a Page", description = "Removes the products on a given page", responses = {
			@ApiResponse(responseCode = "200", description = "Items succesfully removed from the page"),
			@ApiResponse(responseCode = "400", description = "Items were not removed to page"),
			@ApiResponse(responseCode = "500", description = "An error occured, the response should contain more info") })
	ResponseEntity<Boolean> clearPage(
			@RequestBody @Parameter(description = "The Page to have the products removed from") PageInfo page) {
		Boolean success = this.pageDefService.clearPage(page);
		return new ResponseEntity<Boolean>(success, success ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}

}
