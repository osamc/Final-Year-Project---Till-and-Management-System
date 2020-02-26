package com.sam.tillsystem.api;

import java.util.List;

import com.sam.tillsystem.models.page.PageInfo;

public interface PageAPI {
	
	public int createPage(String name, int xcount, int ycount);
	public int createPage(PageInfo page);
	
	public PageInfo getPage(int id);
	public List<PageInfo> getPages();
	
	public int updatePage(PageInfo page);
	
	public boolean deletePage(int id);
	public boolean deletePage(PageInfo page);
	
	
}
