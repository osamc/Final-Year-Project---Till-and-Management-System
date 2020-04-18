package com.sam.tillsystem.service;

import static org.junit.Assert.assertNotNull;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.sam.tillsystem.jwt.JwtUser;
import com.sam.tillsystem.jwt.JwtUserImpl;

@SpringBootTest
@RunWith(SpringRunner.class)
public class JwtUserTests {

	@Autowired
	DataSource ds;
	
	@Autowired
	JwtUserImpl userService;
	
	@Test
	public void testCreateAndRetrieve() {
		
		JwtUser user = new JwtUser();
		user.setUsername("test");
		user.setPassword("test123");
		
		userService.saveUser(user);
		
		JwtUser fromDb = userService.findByUsername("test");
		
		assertNotNull(fromDb);
	}
	
	@Test
	public void testInitialAdmin() {
		JwtUser fromDb = userService.findByUsername("admin");
		assertNotNull(fromDb);
	}
	
}
