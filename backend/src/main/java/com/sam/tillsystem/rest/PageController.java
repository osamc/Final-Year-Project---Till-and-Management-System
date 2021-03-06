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

import com.sam.tillsystem.exceptions.CoordinatesOutOfRangeException;
import com.sam.tillsystem.exceptions.PageInfoNotFoundException;
import com.sam.tillsystem.exceptions.ProductAlreadyExistsException;
import com.sam.tillsystem.exceptions.ProductNotFoundException;
import com.sam.tillsystem.models.page.PageDefinition;
import com.sam.tillsystem.models.page.PageInfo;
import com.sam.tillsystem.service.PageDefinitionImpl;
import com.sam.tillsystem.service.PageImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("page")
@Tag(name = "Page API", description = "The Page API is used for creating and maintaining pages within the Application")
public class PageController {

	@Autowired
	PageImpl pageService;

	@Autowired
	PageDefinitionImpl pageDefService;

	@PostMapping(value = "createPage", consumes = "application/json")
	@Operation(summary = "Creates a page", description = "This method creates a page to be used within the system.", responses = {
			@ApiResponse(responseCode = "200", description = "Page created"),
			@ApiResponse(responseCode = "400", description = "page not created") }, security = @SecurityRequirement(name = "bearerAuth"))
	ResponseEntity<PageInfo> createPage(@RequestBody @Parameter(description = "The page to be created") PageInfo page)
			throws PageInfoNotFoundException, ProductNotFoundException, CoordinatesOutOfRangeException,
			ProductAlreadyExistsException {

		PageInfo created = null;

		try {
			created = this.pageService.createPage(page);
			List<PageDefinition> defs = page.getProductAssociations();
			for (int i = 0; i < defs.size(); i++) {
				PageDefinition def = defs.get(i);
				this.pageDefService.addItemToPage(def.getProduct(), created, def.getX(), def.getY());
			}

			return new ResponseEntity<>(created, created != null ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
		} catch (Exception e) {

			if (created != null) {
				this.pageService.deletePage(created);
			}

			throw e;
		}

	}

	@GetMapping(value = "getPage/{id}", produces = "application/json")
	@Operation(summary = "Gets a page", description = "Gets a page from a given ID.", responses = {
			@ApiResponse(responseCode = "200", description = "Page found"),
			@ApiResponse(responseCode = "404", description = "No page found for given id") }, security = @SecurityRequirement(name = "bearerAuth"))
	ResponseEntity<PageInfo> getPage(@PathVariable("id") @Parameter(description = "The ID of the page") int id) {
		PageInfo page = this.pageService.getPage(id);
		return new ResponseEntity<>(page, page != null ? HttpStatus.OK : HttpStatus.NOT_FOUND);
	}

	@GetMapping(value = "getPages", produces = "application/json")
	@Operation(summary = "Gets a list of all pages", description = "Gets all pages from db.",  security = @SecurityRequirement(name = "bearerAuth"))
	ResponseEntity<List<PageInfo>> getPages() {
		return new ResponseEntity<>(this.pageService.getPages(), HttpStatus.OK);
	}

	@PostMapping(value = "updatePage", consumes = "application/json")
	@Operation(summary = "Updates a page", description = "Updates a page, uses the id the found"
			+ " within the object to update that record within the database", responses = {
					@ApiResponse(responseCode = "200", description = "Page found and updated"),
					@ApiResponse(responseCode = "404", description = "No page found for given id") }, security = @SecurityRequirement(name = "bearerAuth"))
	ResponseEntity<Boolean> updatePage(
			@RequestBody @Parameter(description = "The page to be updated") PageInfo toUpdate)
			throws PageInfoNotFoundException, ProductNotFoundException, CoordinatesOutOfRangeException,
			ProductAlreadyExistsException {
		Boolean success = this.pageService.updatePage(toUpdate) > 0;

		if (success) {
			this.pageDefService.clearPage(toUpdate);
			List<PageDefinition> defs = toUpdate.getProductAssociations();
			for (int i = 0; i < defs.size(); i++) {
				PageDefinition def = defs.get(i);
				this.pageDefService.addItemToPage(def.getProduct(), toUpdate, def.getX(), def.getY());
			}
		}

		return new ResponseEntity<Boolean>(success, success ? HttpStatus.OK : HttpStatus.NOT_FOUND);
	}

	@DeleteMapping(value = "deletePage/{id}")
	@Operation(summary = "Deletes a page", description = "Deletes a page that is associated to a given id", responses = {
			@ApiResponse(responseCode = "200", description = "Page found and deleted"),
			@ApiResponse(responseCode = "404", description = "No page found for given id") }, security = @SecurityRequirement(name = "bearerAuth"))
	ResponseEntity<Boolean> deletePage(
			@PathVariable("id") @Parameter(description = "The id of the page to delete") int id) {
		Boolean deleted = this.pageService.deletePage(id);
		return new ResponseEntity<Boolean>(deleted, deleted ? HttpStatus.OK : HttpStatus.NOT_FOUND);
	}


	@PostMapping(value = "clearPage", consumes = "application/json")
	@Operation(summary = "Removes all items from a Page", description = "Removes the products on a given page", responses = {
			@ApiResponse(responseCode = "200", description = "Items succesfully removed from the page"),
			@ApiResponse(responseCode = "400", description = "Items were not removed to page"),
			@ApiResponse(responseCode = "500", description = "An error occured, the response should contain more info") }, security = @SecurityRequirement(name = "bearerAuth"))
	ResponseEntity<Boolean> clearPage(
			@RequestBody @Parameter(description = "The Page to have the products removed from") PageInfo page) {
		Boolean success = this.pageDefService.clearPage(page);
		return new ResponseEntity<Boolean>(success, success ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}

}
