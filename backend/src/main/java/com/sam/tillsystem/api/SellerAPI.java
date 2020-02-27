package com.sam.tillsystem.api;

import com.sam.tillsystem.models.user.Seller;

public interface SellerAPI {

	public Seller createSeller(String name, String loginCode);
	
	public Seller getSeller(int id);
	
	public boolean updateSeller(Seller seller);
	
	public boolean removeSeller(Seller seller);
	public boolean removeSeller(int id);
	
	
}
