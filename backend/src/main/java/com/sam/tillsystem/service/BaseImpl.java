package com.sam.tillsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


public class BaseImpl {

protected final JdbcTemplate template;
	
	@Autowired
	public BaseImpl(JdbcTemplate template) {
		this.template = template;
	}
	
	
}
