package com.sam.tillsystem.api;

import com.sam.tillesystem.exceptions.CoordinatesOutOfRangeException;
import com.sam.tillesystem.exceptions.PageInfoNotFoundException;
import com.sam.tillesystem.exceptions.ProductAlreadyExistsException;
import com.sam.tillesystem.exceptions.ProductNotFoundException;
import com.sam.tillsystem.models.Product.Product;
import com.sam.tillsystem.models.page.PageInfo;

public interface PageDefinitionAPI {

	public boolean addItemToPage(Product product, PageInfo page, int x, int y) throws PageInfoNotFoundException,
			ProductNotFoundException, CoordinatesOutOfRangeException, ProductAlreadyExistsException;

	public boolean addItemToPage(int productId, int pageId, int x, int y) throws PageInfoNotFoundException,
			ProductNotFoundException, CoordinatesOutOfRangeException, ProductAlreadyExistsException;

	public boolean removeItemFromPage(PageInfo page, int x, int y)
			throws PageInfoNotFoundException, ProductNotFoundException;

	public boolean clearPage(PageInfo page) throws PageInfoNotFoundException;
}
