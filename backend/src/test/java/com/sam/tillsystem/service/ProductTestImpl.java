package com.sam.tillsystem.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.sam.tillsystem.models.product.Product;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ProductTestImpl {

	@Autowired
	ProductImpl service;
	
	@Autowired
	DataSource ds;
	
	@Before
	//Make sure to clear the database before each test is called
	public void beforeTest() throws SQLException {
		
		try (Connection con = ds.getConnection()) {
			con.createStatement().execute("DELETE FROM product WHERE 1=1");
			con.createStatement().execute("ALTER TABLE product ALTER COLUMN id RESTART WITH 1");
		}
		
	}
	
	@Test
	public void testCreateProduct() {
		Product product = new Product();
		product.setImage("http://google.com");
		product.setName("name");
		product.setInfo("info");
		product.setPrice(2.0);
		
		Product fromDb = service.createProduct(product);
		
		assertNotNull(fromDb);
		assertEquals(fromDb.getId(), 1);
		
		product.setId(1);
		
		assertTrue(product.equals(fromDb));
		
	}
	
	@Test
	public void testCreateMultipleProducts() {
		Product product = new Product();
		product.setImage("http://google.com");
		product.setName("name");
		product.setInfo("info");
		product.setPrice(2.0);
		
		Product fromDb1 = service.createProduct(product);
		product.setName("name2");
		Product fromDb2 = service.createProduct(product);
		
		assertTrue(fromDb1.getId() != fromDb2.getId());

	}
	
	@Test
	public void testUpdateProduct() {
		Product product = new Product();
		product.setImage("http://google.com");
		product.setName("name");
		product.setInfo("info");
		product.setPrice(2.0);
		product = service.createProduct(product);
		
		product.setInfo("updated info");
		service.updateProduct(product);
		product.setInfo("info");
		
		Product updated = service.getProduct(product.getId());
		
		assertTrue(!updated.equals(product));
		assertEquals(product.getId(), updated.getId());
		assertEquals(product.getImage(), updated.getImage());
		assertEquals(product.getPrice(), updated.getPrice());
		
	}
	
	@Test
	public void testDelete() {
		Product product = new Product();
		product.setImage("http://google.com");
		product.setName("name");
		product.setInfo("info");
		product.setPrice(2.0);
		for(int i = 0; i < 10; i++) {
			service.createProduct(product);
		}
		
		List<Product> initial = service.getProducts();
		
		service.deleteProduct(2);
		service.deleteProduct(5);
		service.deleteProduct(9);
		
		List<Product> after = service.getProducts();

		assertTrue(initial.size() > after.size());
		
		List<Integer> ids = after.stream().map(Product::getId).collect(Collectors.toList());
		
		assertTrue(!ids.contains(2));
		assertTrue(!ids.contains(5));
		assertTrue(!ids.contains(9));

	}
	
	@Test
	public void testGetProduct() {
		Product product = new Product();
		product.setImage("http://google.com");
		product.setName("name");
		product.setInfo("info");
		product.setPrice(2.0);
		
		int numberToAdd = 10;
		
		for(int i = 0; i < numberToAdd; i++) {
			service.createProduct(product);
		}
		
		Random r = new Random();
		
		Product fromDb = service.getProduct(r.nextInt(numberToAdd - 1));
		
		assertNotNull(fromDb);
		
	}
	
	@Test
	public void testGetProducts() {
		Product product = new Product();
		product.setImage("http://google.com");
		product.setName("name");
		product.setInfo("info");
		product.setPrice(2.0);
		
		int numberToAdd = 10;
		
		for(int i = 0; i < numberToAdd; i++) {
			service.createProduct(product);
		}
		
		List<Product> products = service.getProducts();
		
		assertTrue(products.size() == numberToAdd);
		
	}
		
	
	
}
