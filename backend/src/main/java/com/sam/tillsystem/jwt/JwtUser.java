package com.sam.tillsystem.jwt;

/**
 * The application user used to authenticate the user for access to the REST API
 * 
 * @author Sam
 *
 */
public class JwtUser {

	private String username;
	private String password;
	private Boolean isFirstLogin;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getFirstLogin() {
		return isFirstLogin;
	}

	public void setFirstLogin(Boolean isFirstLogin) {
		this.isFirstLogin = isFirstLogin;
	}

}
