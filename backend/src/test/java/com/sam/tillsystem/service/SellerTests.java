package com.sam.tillsystem.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.sam.tillsystem.models.user.Seller;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SellerTests {

	@Autowired
	DataSource ds;
	
	@Autowired
	SellerImpl service;
	
	@Before
	//Make sure to clear the database before each test is called
	public void beforeTest() throws SQLException {
		
		try (Connection con = ds.getConnection()) {
			con.createStatement().execute("DELETE FROM seller WHERE 1=1");
			con.createStatement().execute("ALTER TABLE seller ALTER COLUMN id RESTART WITH 1");
		}
		
	}
	
	@Test
	public void testCreateUser() {
		Seller seller = service.createSeller("test", "1234");
		assertTrue(seller.getId() == 1);
	}
	
	@Test
	public void testGetUsers() {
		for (int i = 0; i < 10; i++) {
			service.createSeller("test", "100");
		}
		
		List<Seller> sellers = service.getSellers();
		
		assertTrue(sellers.size() == 10);
	}
	
	@Test
	public void testGetUser() {
		service.createSeller("test", "1234");
		Seller fromDb = service.getSeller(1);
		
		assertTrue(fromDb != null);
		assertEquals(fromDb.getName(), "test");
		assertEquals(fromDb.getLogin(), "1234");
	}
	
	@Test
	public void testGetNonexistent() {
		Seller s = service.getSeller(1);
		assertNull(s);
	}
	
	@Test
	public void testUpdateUser() {
		Seller initial = service.createSeller("test", "1234");
		service.updateSeller(new Seller().setId(1).setName("bob").setLogin("1234"));
		Seller fromDb = service.getSeller(1);
		assertTrue(fromDb.getName().equals("bob"));
		assertTrue(fromDb.getLogin().equals("1234"));
		assertFalse(fromDb.getName().equals("test"));
	}
	
	@Test
	public void testRemoveSeller() {
		service.createSeller("test", "1234");
		Seller fromDb = service.getSeller(1);
		
		assertTrue(fromDb != null);
		
		service.removeSeller(fromDb);
		fromDb = service.getSeller(1);
		
		assertTrue(fromDb == null);
	}
	
}
