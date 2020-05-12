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
import com.sam.tillsystem.jwt.PasswordRequest;

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

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@PostMapping(value = "/update", consumes = "application/json")
	@Operation(summary = "updates the details associated with the user", description = "Allows the user to update the details associated with the account", security = @SecurityRequirement(name = "bearerAuth"), responses = {
			@ApiResponse(responseCode = "200", description = "User updated") })
	public void updateUser(@RequestBody JwtUser user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		applicationUserRepository.updateUser(user);
	}

	@PostMapping(value = "/preq", consumes = "application/json")
	@Operation(summary = "Updates the logged in users password", description = "Takes a password request object, compares the given password to the one stored"
			+ " in the database. If they are the same then the password is updated.", security = @SecurityRequirement(name = "bearerAuth"), responses = {
					@ApiResponse(responseCode = "200", description = "User updated") })
	public ResponseEntity<Boolean> updatePass(@RequestBody PasswordRequest req) {
		boolean success = this.applicationUserRepository.passwordUpdate(req);
		return new ResponseEntity<Boolean>(success, HttpStatus.OK);

	}

	@GetMapping(value = "/tokentest")
	@Operation(summary = "Allows testing for Tokens", description = "Allows the user to test if a token is valid", security = @SecurityRequirement(name = "bearerAuth"), responses = {
			@ApiResponse(responseCode = "200", description = "Token valid") })
	public ResponseEntity<Boolean> tokenTest() {
		// This will return true as to even get a response from the service, the token
		// will need
		// to be valid
		return new ResponseEntity<Boolean>(true, HttpStatus.OK);
	}
	
	@GetMapping(value = "firstlogin")
	@Operation(summary = "Allows checking to see if the user needs to update their password", description = "Allows checking to see if the logged in user has to change their password"
			, security = @SecurityRequirement(name = "bearerAuth"))
	public ResponseEntity<Boolean> userUpdateCheck() {
		boolean response = this.applicationUserRepository.getFirstLogin();
		return new ResponseEntity<Boolean>(response, HttpStatus.OK);
	}

}
