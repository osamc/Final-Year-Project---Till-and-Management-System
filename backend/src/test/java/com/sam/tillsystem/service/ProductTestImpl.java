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

import com.sam.tillsystem.models.product.Group;
import com.sam.tillsystem.models.product.Product;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ProductTestImpl {

	@Autowired
	ProductImpl service;

	@Autowired
	DataSource ds;

	@Before
	// Make sure to clear the database before each test is called
	public void beforeTest() throws SQLException {

		try (Connection con = ds.getConnection()) {
			con.createStatement().execute("DELETE FROM product WHERE 1=1");
			con.createStatement().execute("ALTER TABLE product ALTER COLUMN id RESTART WITH 1");
			con.createStatement().execute("DELETE FROM product_group WHERE 1=1");
			con.createStatement().execute("ALTER TABLE product_group ALTER COLUMN group_id RESTART WITH 1");
			con.createStatement().execute("INSERT INTO product_group (group_name, system) VALUES ('No Group', true)");
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
		for (int i = 0; i < 10; i++) {
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

		service.createProduct(product);

		Product fromDb = service.getProduct(1);

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

		for (int i = 0; i < numberToAdd; i++) {
			service.createProduct(product);
		}

		List<Product> products = service.getProducts();

		assertTrue(products.size() == numberToAdd);

	}

	@Test
	public void testCreateGroup() {
		Group group = service.createGroup("Group");
		assertNotNull(group);
	}

	@Test
	public void testCreateProductWithGroup() {
		Group group = service.createGroup("Group");
		Product product = new Product();
		product.setImage("http://google.com");
		product.setName("name");
		product.setInfo("info");
		product.setPrice(2.0);
		product.setGroup(group);
		service.createProduct(product);

		List<Group> groups = service.getGroups(true);

		assertTrue(groups.size() == 2);
		assertTrue(groups.get(1).getProducts().size() == 1);
	}

	@Test
	public void testProductNoGroup() {
		Product product = new Product();
		product.setImage("http://google.com");
		product.setName("name");
		product.setInfo("info");
		product.setPrice(2.0);
		service.createProduct(product);

		List<Group> groups = service.getGroups(true);

		assertTrue(groups.size() == 1);
		assertTrue(groups.get(0).getProducts().size() == 1);

	}
	
	@Test
	public void testUpdateGroup() {
		Group group = service.createGroup("name");
		service.updateGroup(new Group().setName("name2").setId(group.getId()));
		Group updated = service.getLastGroup();
		assertTrue(!updated.getName().equals(group.getName()));
		
	}
	
	@Test
	public void testRemoveGroup() {
		Group group = service.createGroup("new");
		
		Product product = new Product();
		product.setImage("http://google.com");
		product.setName("name");
		product.setInfo("info");
		product.setPrice(2.0);
		product.setGroup(group);
		service.createProduct(product);
		
		List<Group> before = service.getGroups(true);
		
		service.deleteGroup(group);
		
		List<Group> after = service.getGroups(true);
	}
	
	@Test
	public void testGetGroup() {
		Group group = service.createGroup("new");

		Group fromDb = service.getGroup(group.getId(), false);
		
		assertTrue(fromDb != null);
		assertTrue(fromDb.getName().equals(group.getName()));   
	}
	
}
