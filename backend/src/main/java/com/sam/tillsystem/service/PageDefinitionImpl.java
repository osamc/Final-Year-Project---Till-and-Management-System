package com.sam.tillsystem.service;

import java.sql.Types;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.InvalidResultSetAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.sam.tillsystem.api.PageDefinitionAPI;
import com.sam.tillsystem.exceptions.CoordinatesOutOfRangeException;
import com.sam.tillsystem.exceptions.PageInfoNotFoundException;
import com.sam.tillsystem.exceptions.ProductAlreadyExistsException;
import com.sam.tillsystem.exceptions.ProductNotFoundException;
import com.sam.tillsystem.models.page.PageInfo;
import com.sam.tillsystem.models.product.Product;

@Repository
public class PageDefinitionImpl extends BaseImpl implements PageDefinitionAPI {

	@Autowired
	private PageImpl pageService;

	@Autowired
	private ProductImpl productService;

	public PageDefinitionImpl(JdbcTemplate template) {
		super(template);
	}

	@Override
	public boolean addItemToPage(Product product, PageInfo page, int x, int y) throws PageInfoNotFoundException,
			ProductNotFoundException, CoordinatesOutOfRangeException, ProductAlreadyExistsException {
		return addItemToPage(product.getId(), page.getInfoId(), x, y);
	}

	@Override
	public boolean addItemToPage(int productId, int pageId, int x, int y) throws PageInfoNotFoundException,
			ProductNotFoundException, CoordinatesOutOfRangeException, ProductAlreadyExistsException {

		PageInfo page = pageService.getPage(pageId);
		Product product = productService.getProduct(productId);

		if (page == null) {
			throw new PageInfoNotFoundException("Page with id: " + pageId + " was not found in the database.");
		}

		if (product == null) {
			throw new ProductNotFoundException("Product with id: " + productId + " was not found in the database.");
		}

		if (page.getXRows() - 1 < x || page.getYRows() - 1 < y || x < 0 || y < 0) {
			throw new CoordinatesOutOfRangeException(
					"Given coordinates: X:" + x + ", Y:" + y + " are not within the bounds of x: 0 - "
							+ (page.getXRows() - 1) + " and y: 0 - " + (page.getYRows() - 1));
		}

		int existsAlready = this.template.queryForObject("SELECT COUNT(*) FROM page_def WHERE x=? AND y=? AND page_id=?",
				new Object[] { x, y, pageId}, new int[] { Types.INTEGER, Types.INTEGER, Types.INTEGER }, Integer.class);

		if (existsAlready > 0) {
			throw new ProductAlreadyExistsException(
					"There is already a product the coodinate location of X: " + x + " Y: " + y);
		}

		return this.template.update("INSERT INTO page_def (page_id, product_id, x, y) VALUES (?,?,?,?)",
				new Object[] { page.getInfoId(), product.getId(), x, y },
				new int[] { Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.INTEGER }) > 0;

	}

	@Override
	public boolean removeItemFromPage(PageInfo page, int x, int y) throws ProductNotFoundException {
		String productId;
		try {
			productId = this.template.queryForObject("SELECT product_id FROM page_def WHERE x=? AND y=?",
					new Object[] { x, y }, new int[] { Types.INTEGER, Types.INTEGER }, String.class);
		} catch (InvalidResultSetAccessException | EmptyResultDataAccessException e) {
			throw new ProductNotFoundException(
					"Product at X: " + x + ", Y: " + y + " not found on page: " + page.getInfoId());
		}

		return template.update("DELETE FROM page_def WHERE page_id=? AND product_id=? AND x=? AND y=?",
				new Object[] { page.getInfoId(), productId, x, y },
				new int[] { Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.INTEGER }) > 0;

	}

	@Override
	public boolean clearPage(PageInfo page) {

		if (pageService.getPage(page.getInfoId()) != null) {

			return this.template.update("DELETE FROM page_def WHERE page_id=?", new Object[] { page.getInfoId() },
					new int[] { Types.INTEGER }) > 0;

		}

		return false;
	}

}
