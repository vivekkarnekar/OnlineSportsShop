package com.app.service;

import java.util.List;

import com.app.dto.VendorDto;
import com.app.pojos.Vendor;

public interface IVendorService {
	
	List<Vendor> getAllVendors();
	
	//Vendor getVendorByName(String vName);	
	
	Vendor getVendorById(int id);
	
	Vendor addVendor(Vendor transientPOJO);

	VendorDto updateVendor(int vendorID, Vendor detachedPOJO);
	
	String deleteVendor(int vendorID);
	
	Vendor getVendorByEmailAndPassword(String email, String password);

	Vendor saveVendor(Vendor vendor);
}
