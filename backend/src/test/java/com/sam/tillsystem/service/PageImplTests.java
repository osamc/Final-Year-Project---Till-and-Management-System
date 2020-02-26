package com.sam.tillsystem.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
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

import com.sam.tillsystem.models.page.PageInfo;


@SpringBootTest
@RunWith(SpringRunner.class)
public class PageImplTests {

	@Autowired
	PageImpl service;
	
	@Autowired
	DataSource ds;
	
	@Before
	//Make sure to clear the database before each test is called
	public void beforeTest() throws SQLException {
		
		try (Connection con = ds.getConnection()) {
			con.createStatement().execute("DELETE FROM page_info WHERE 1=1");
			con.createStatement().execute("ALTER TABLE page_info ALTER COLUMN infoid RESTART WITH 1");
		}
		
	}
	
	
	@Test
	public void testCreatePage() {
		PageInfo info = new PageInfo();
		info.setName("test").setXRows(3).setYRows(2);
		service.createPage(info);

		PageInfo fromDb = service.getPage(1);
		
		assertTrue(fromDb.getName().equals(info.getName()));
		assertTrue(fromDb.getXRows() == info.getXRows());
		assertTrue(fromDb.getYRows() == info.getYRows());

	}
	
	@Test
	public void testCreatePages() {
		PageInfo info = new PageInfo();
		info.setName("test").setXRows(3).setYRows(2);
		service.createPage(info);
		info.setName("test2");
		service.createPage(info);
		
		PageInfo fromDb = service.getPage(2);
		
		assertTrue(fromDb.getName().equals(info.getName()));
		assertTrue(fromDb.getXRows() == info.getXRows());
		assertTrue(fromDb.getYRows() == info.getYRows());
		
	}
	
	@Test
	public void testGetPages() {
		PageInfo info = new PageInfo();
		info.setName("test").setXRows(3).setYRows(2);
		int count = 10;
		
		for (int i = 0; i < count; i++) {
			service.createPage(info);
		}
		
		List<PageInfo> pages = service.getPages();
		assertTrue(pages.size() == count);
	}
	
	@Test
	public void deletePage() {
		PageInfo info = new PageInfo();
		info.setName("test").setXRows(3).setYRows(2);
		service.createPage(info);
		
		List<PageInfo> pages = service.getPages();
		
		service.deletePage(1);
		
		assertTrue(pages.size() == (service.getPages().size() + 1));
	}
	
	@Test
	public void updatePage() {
		PageInfo info = new PageInfo();
		info.setName("test").setXRows(3).setYRows(2);
		service.createPage(info);
		
		PageInfo fromDb = service.getPage(1);
		fromDb.setName("new name");
		service.updatePage(fromDb);
		fromDb = service.getPage(1);
		
		assertTrue(!fromDb.getName().equals(info.getName()));
		assertTrue(fromDb.getXRows() == info.getXRows());
		assertTrue(fromDb.getYRows() == info.getYRows());
	}
	
	@Test
	public void getPageThatDoesntExist() {
		PageInfo info = service.getPage(2);
		assertNull(info);
	}

	
}
