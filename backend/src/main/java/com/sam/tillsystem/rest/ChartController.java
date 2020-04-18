package com.sam.tillsystem.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sam.tillsystem.service.ChartImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("chart")
@Tag(name = "Chart API", description = "The Chart API is used for getting graph data to be shown within the application")

public class ChartController {

	@Autowired
	ChartImpl chartService;

	@GetMapping(value = "getOptions", produces = "application/json")
	@Operation(summary = "Gets a list of available charts", description = "Returns a list of strings of charts that the system is able to produce", security = @SecurityRequirement(name = "bearerAuth"))
	ResponseEntity<List<String>> getAvailableCharts() {
		return new ResponseEntity<List<String>>(this.chartService.availableCharts(), HttpStatus.OK);
	}

	@GetMapping(value = "getChart/{name}", produces = "application/json")
	@Operation(summary = "Returns Chart associated with selected Option", description = "Returns the associated JSON to create the chosen chart within the application", security = @SecurityRequirement(name = "bearerAuth"))
	ResponseEntity<String> getChart(@PathVariable("name") @Parameter(description = "The chart to get") String option) {
		return new ResponseEntity<String>(this.chartService.getChart(option).toJson(), HttpStatus.OK);
	}

}
