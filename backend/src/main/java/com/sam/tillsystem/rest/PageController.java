package com.sam.tillsystem.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sam.tillsystem.models.page.PageInfo;
import com.sam.tillsystem.service.PageDefinitionImpl;
import com.sam.tillsystem.service.PageImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
	@Operation(summary = "Creates a page", description = "This method creates a page")
	PageInfo createPage(@RequestBody @Parameter(description = "The page to be created") PageInfo page) {
		return pageService.createPage(page);
	}
	
	
	
}
