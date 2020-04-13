package com.sam.tillsystem.models.user;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Seller {

	private int id;
	
	private String name;
	
	private String loginCode;
	
	public Seller() {
		
	}
	
	public int getId() {
		return this.id;
	}
	
	public Seller setId(int id) {
		this.id = id;
		return this;
	}
	
	public String getName() {
		return this.name;
	}
	
	public Seller setName(String name) {
		this.name = name;
		return this;
	}
	
	@JsonIgnore
	public String getLogin() {
		return this.loginCode;
	}
	
	public Seller setLogin(String login) {
		this.loginCode = login;
		return this;
	}
	
}
