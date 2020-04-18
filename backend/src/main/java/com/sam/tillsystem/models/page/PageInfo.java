package com.sam.tillsystem.models.page;

import java.util.List;

/**
 * An object representing Page information from within the database
 * @author Sam
 *
 */
public class PageInfo {

	private int infoId;

	private String name;

	private int xRows;

	private int yRows;
	
	private List<PageDefinition> productAssociations;
	
	public int getInfoId() {
		return this.infoId;
	}
	
	public PageInfo setInfoId(int id) {
		this.infoId = id;
		return this;
	}
	
	public String getName() {
		return this.name;
	}
	
	public PageInfo setName(String name) {
		this.name = name;
		return this;
	}
	
	public int getXRows() {
		return this.xRows;
	}
	
	public PageInfo setXRows(int rows) {
		this.xRows = rows;
		return this;
	}
	
	public int getYRows() {
		return this.yRows;
	}
	
	public PageInfo setYRows(int rows) {
		this.yRows = rows;
		return this;
	}
	
	public List<PageDefinition> getProductAssociations() {
		return this.productAssociations;
	}
	
	public PageInfo setProductAssociations(List<PageDefinition> productAssociations) {
		this.productAssociations = productAssociations;
		return this;
	}	
	
}
 