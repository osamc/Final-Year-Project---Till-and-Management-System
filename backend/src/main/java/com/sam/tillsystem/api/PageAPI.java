package com.sam.tillsystem.api;

import java.util.List;

import com.sam.tillsystem.models.page.PageInfo;

/**
 * The page API, used for creating pages and maintaining pages.
 * @author Sam
 *
 */
public interface PageAPI {
	
	/**
	 * Creates a page 
	 * @param name the alias of the page
	 * @param xcount the number of rows in the x axis
	 * @param ycount the number of rows in the y axis
	 * @return the created {@link PageInfo}
	 */
	public PageInfo createPage(String name, int xcount, int ycount);
	
	/**
	 * Creates a page from the {@link PageInfo}
	 * @param page page to create
	 * @return the created {@link PageInfo}
	 */
	public PageInfo createPage(PageInfo page);
	
	/**
	 * Gets a given page
	 * @param id the id of the page to find
	 * @return the {@link PageInfo} if found, else returns null
	 */
	public PageInfo getPage(int id);
	
	/**
	 * Gets a list of all pages
	 * @return a {@link List} of {@link PageInfo}
	 */
	public List<PageInfo> getPages();
	
	/**
	 * Updates a given page
	 * @param page to be updated
	 * @return the number of rows affected in the database. (0 means nothing was changed).
	 */
	public int updatePage(PageInfo page);
	
	/**
	 * Deletes a given page
	 * @param id id of page to be deleted
	 * @return returns success flag
	 */
	public boolean deletePage(int id);
	
	/**
	 * Deletes a given page
	 * @param page the {@link PageInfo} to delete
	 * @return returns success flag
	 */
	public boolean deletePage(PageInfo page);
	
	
}
