package com.sam.tillsystem.api;

import com.sam.tillesystem.exceptions.CoordinatesOutOfRangeException;
import com.sam.tillesystem.exceptions.PageInfoNotFoundException;
import com.sam.tillesystem.exceptions.ProductAlreadyExistsException;
import com.sam.tillesystem.exceptions.ProductNotFoundException;
import com.sam.tillsystem.models.page.PageInfo;
import com.sam.tillsystem.models.product.Product;

/**
 * The page definition API is responsible for maintaining pages and their associations with products
 * @author Sam
 *
 */
public interface PageDefinitionAPI {

	/**
	 * Adds a {@link Product} to a Page Definition
	 * @param product the {@link Product} to link
	 * @param page the {@link PageInfo} to be linked 
	 * @param x the x coordinate on the page
	 * @param y the y coordinate on the page
	 * @return returns success flag
	 * @throws PageInfoNotFoundException if the page is not found
	 * @throws ProductNotFoundException if the product is not found
	 * @throws CoordinatesOutOfRangeException if the coordinate space is invalid
	 * @throws ProductAlreadyExistsException  if the coordinates are already occupied
	 */
	public boolean addItemToPage(Product product, PageInfo page, int x, int y) throws PageInfoNotFoundException,
			ProductNotFoundException, CoordinatesOutOfRangeException, ProductAlreadyExistsException;

	/**
	 * Adds a product to a Page Definition
	 * @param productId the id of the product
	 * @param pageId the id of the page
	 * @param x the x coordinate on the page
	 * @param y the y coordinate on the page
	 * @return returns success flag
	  @throws PageInfoNotFoundException if the page is not found
	 * @throws ProductNotFoundException if the product is not found
	 * @throws CoordinatesOutOfRangeException if the coordinate space is invalid
	 * @throws ProductAlreadyExistsException  if the coordinates are already occupied
	 */
	public boolean addItemToPage(int productId, int pageId, int x, int y) throws PageInfoNotFoundException,
			ProductNotFoundException, CoordinatesOutOfRangeException, ProductAlreadyExistsException;

	/**
	 * Removes an item from a given {@link PageInfo}
	 * @param page Page to have the item removed from
	 * @param x the x coordinate of the product
	 * @param y the y coordinate of the product
	 * @return returns success flag
	 * @throws PageInfoNotFoundException if no page was found
	 * @throws ProductNotFoundException if no product was found
	 */
	public boolean removeItemFromPage(PageInfo page, int x, int y)
			throws PageInfoNotFoundException, ProductNotFoundException;

	/**
	 * Clears a given page
	 * @param page {@link PageInfo} to be cleared
	 * @return returns success flag
	 * @throws PageInfoNotFoundException
	 */
	public boolean clearPage(PageInfo page) throws PageInfoNotFoundException;
}
