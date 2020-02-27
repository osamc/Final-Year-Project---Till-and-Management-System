package com.sam.tillsystem.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.sam.tillesystem.exceptions.CoordinatesOutOfRangeException;
import com.sam.tillesystem.exceptions.PageInfoNotFoundException;
import com.sam.tillesystem.exceptions.ProductAlreadyExistsException;
import com.sam.tillesystem.exceptions.ProductNotFoundException;
import com.sam.tillsystem.models.Product.Product;
import com.sam.tillsystem.models.page.PageDefinition;
import com.sam.tillsystem.models.page.PageInfo;

@SpringBootTest
@RunWith(SpringRunner.class)
public class PageDefinitionTests {

	@Autowired
	DataSource ds;

	@Autowired
	PageImpl pageService;

	@Autowired
	ProductImpl productService;

	@Autowired
	PageDefinitionImpl service;

	@Before
	// Make sure to clear the database before each test is called
	public void beforeTest() throws SQLException {

		try (Connection con = ds.getConnection()) {
			// clear the page info table
			con.createStatement().execute("DELETE FROM page_info WHERE 1=1");
			con.createStatement().execute("ALTER TABLE page_info ALTER COLUMN infoid RESTART WITH 1");
			// clear the product table
			con.createStatement().execute("DELETE FROM product WHERE 1=1");
			con.createStatement().execute("ALTER TABLE product ALTER COLUMN id RESTART WITH 1");
			// clear the pagedef table
			con.createStatement().execute("DELETE FROM page_def WHERE 1=1");
		}

	}

	private Product createProduct() {
		Product product = new Product();
		product.setImage("image");
		product.setInfo("set info");
		product.setName("test");
		product.setPrice(2.0);

		return productService.createProduct(product);
	}

	private PageInfo createPageInfo(int x, int y) {
		PageInfo page = new PageInfo();
		page.setName("Test page");
		page.setXRows(x);
		page.setYRows(y);
		return pageService.createPage(page);
	}

	@Test
	public void testCreateDefinition() throws PageInfoNotFoundException, ProductNotFoundException,
			CoordinatesOutOfRangeException, ProductAlreadyExistsException {

		Product product = createProduct();
		PageInfo page = createPageInfo(5, 5);

		assertTrue(service.addItemToPage(product.getId(), page.getInfoId(), 0, 0));

	}

	@Test(expected = CoordinatesOutOfRangeException.class)
	public void testPlacingProductNegativeCoords() throws PageInfoNotFoundException, ProductNotFoundException,
			CoordinatesOutOfRangeException, ProductAlreadyExistsException {
		Product product = createProduct();
		PageInfo page = createPageInfo(5, 5);

		service.addItemToPage(product.getId(), page.getInfoId(), -1, -1);
	}

	@Test(expected = CoordinatesOutOfRangeException.class)
	public void testPlacingProductCoordsToLarge() throws PageInfoNotFoundException, ProductNotFoundException,
			CoordinatesOutOfRangeException, ProductAlreadyExistsException {
		Product product = createProduct();
		PageInfo page = createPageInfo(5, 5);

		service.addItemToPage(product.getId(), page.getInfoId(), 6, 6);
	}
	
	@Test(expected = ProductAlreadyExistsException.class) 
	public void testCollision() throws PageInfoNotFoundException, ProductNotFoundException, CoordinatesOutOfRangeException, ProductAlreadyExistsException {
		Product product = createProduct();
		PageInfo page = createPageInfo(5, 5);
		service.addItemToPage(product.getId(), page.getInfoId(), 3, 3);
		service.addItemToPage(product.getId(), page.getInfoId(), 3, 3);
	}
	
	@Test
	public void testRemoveItemFromPage() throws PageInfoNotFoundException, ProductNotFoundException, CoordinatesOutOfRangeException, ProductAlreadyExistsException {
		Product product = createProduct();
		PageInfo page = createPageInfo(5, 5);
		service.addItemToPage(product.getId(), page.getInfoId(), 3, 3);
		assertTrue(service.removeItemFromPage(page, 3, 3));
	}
	
	@Test(expected = ProductNotFoundException.class)
	public void testRemoveNonExistentItemFromPage() throws ProductNotFoundException {
		PageInfo page = createPageInfo(5, 5);
		service.removeItemFromPage(page, 3, 3);
	}
	
	@Test
	public void testClearPage() throws PageInfoNotFoundException, ProductNotFoundException, CoordinatesOutOfRangeException, ProductAlreadyExistsException {
		Product product = createProduct();
		PageInfo page = createPageInfo(5, 5);
		service.addItemToPage(product.getId(), page.getInfoId(), 3, 3);
		
		assertTrue(service.clearPage(page));
	}
	
	@Test
	public void testClearEmptyPage() {
		PageInfo page = createPageInfo(5, 5);
		assertFalse(service.clearPage(page));
	}
	
	@Test
	public void testClearPageThatDoesntExist() {
		assertFalse(service.clearPage(new PageInfo().setInfoId(2)));
	}
	
	@Test
	public void testGetPageWithDef() throws PageInfoNotFoundException, ProductNotFoundException, CoordinatesOutOfRangeException, ProductAlreadyExistsException {
		Product product = createProduct();
		PageInfo page = createPageInfo(5, 5);
		service.addItemToPage(product.getId(), page.getInfoId(), 3, 3);
		
		PageInfo fromDB = pageService.getPage(page.getInfoId());
		PageDefinition def = fromDB.getPageDefinitions().get(0);
		assertTrue(def.getX() == 3);
		assertTrue(def.getY() == 3);
		assertTrue(def.getProduct().getId() == product.getId());

	}

}
