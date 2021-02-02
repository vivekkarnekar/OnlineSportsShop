package com.app.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.custom_excs.OrderNotFoundException;
import com.app.dao.IVendorDao;
import com.app.dto.VendorDto;
import com.app.pojos.Vendor;

@Service
@Transactional
public class VendorServiceImpl implements IVendorService {
	// dependency
	@Autowired
	private IVendorDao dao;

	@Override
	public List<Vendor> getAllVendors() {
		System.out.println("dao imple class " + dao.getClass().getName());
		return dao.findAll();
	}
	
	@Override
	public Vendor saveVendor(Vendor vendor) {
		return dao.save(vendor);
	}
	
	@Override 
	public Vendor getVendorByEmailAndPassword(String email, String password) { 
		return dao.findByEmailAndPassword(email, password); 
	}
	
	@Override 
	public Vendor getVendorById(int id) 
	{	  
		return dao.findById(id); 
	}
	 
		
	@Override
	public Vendor addVendor(Vendor transientPOJO) {
		// TODO Auto-generated method stub
		return dao.save(transientPOJO);
	}

	@Override
	public VendorDto updateVendor(int vendorID, Vendor existingVendor) {
		// chk if Order exists : findById
		Vendor updatedVendor = dao.findById(vendorID);
		if (updatedVendor != null) {
			updatedVendor.setContactNumber(existingVendor.getContactNumber());
			updatedVendor.setPassword(existingVendor.getPassword());
			updatedVendor.setStoreName(existingVendor.getStoreName());
			Vendor order = dao.save(existingVendor);
			VendorDto finalVendor = new VendorDto();
			BeanUtils.copyProperties(order, finalVendor);
			return finalVendor;
		}
		throw new OrderNotFoundException("Invalid Vendor ID");
	}

	@Override
	public String deleteVendor(int vendorId) {
		dao.deleteById(vendorId);
		return "Vendor with ID " + vendorId + " deleted";
	}
	
}













