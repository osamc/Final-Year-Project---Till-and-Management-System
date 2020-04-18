package com.sam.tillsystem.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sam.tillsystem.jwt.JwtUser;
import com.sam.tillsystem.jwt.JwtUserAPI;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("jwtuser")
@Tag(name = "JWT User API", description = "The API is used for JWT Token interactions, such as updating a user or testing if a token is valid")
public class JwtUserController {

	@Autowired
	private JwtUserAPI applicationUserRepository;

	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public JwtUserController(JwtUserAPI applicationUserRepository,
			BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.applicationUserRepository = applicationUserRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	@PostMapping(value = "/update",  consumes = "application/json")
	@Operation(summary = "updates the details associated with the user", description = "Allows the user to update the details associated with the account", security = @SecurityRequirement(name = "bearerAuth"), responses = {
			@ApiResponse(responseCode = "200", description = "User updated") })
	public void signUp(@RequestBody JwtUser user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		applicationUserRepository.updateUser(user);
	}

	@GetMapping(value = "/tokentest")
	@Operation(summary = "Allows testing for Tokens", description = "Allows the user to test if a token is valid", security = @SecurityRequirement(name = "bearerAuth"), responses = {
			@ApiResponse(responseCode = "200", description = "Token valid") })
	public ResponseEntity<Boolean> tokenTest() {
		return new ResponseEntity<Boolean>(true, HttpStatus.OK);
	}

}
