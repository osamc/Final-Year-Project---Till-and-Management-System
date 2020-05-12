package com.sam.tillsystem.jwt;

public class PasswordRequest {

	private String currentPass;
	private String newPass;
	
	
	public String getCurrentPass() {
		return currentPass;
	}
	public PasswordRequest setCurrentPass(String currentPass) {
		this.currentPass = currentPass;
		return this;
	}
	public String getNewPass() {
		return newPass;
	}
	public PasswordRequest setNewPass(String newPass) {
		this.newPass = newPass;
		return this;
	}
	
}
