package com.app.service;

import java.util.List;

import com.app.dto.ProductDto;
import com.app.pojos.Category;
import com.app.pojos.Product;

public interface IProductService {
	// list all Products
	List<ProductDto> getAllProducts();

	// get Product details by name
	ProductDto getProductByName(String pName);	
	
	// get Product details by id
	ProductDto getProductById(int id);

	// add new Product details
	Integer addProduct(ProductDto transientPOJO);

	// update existing Product details
	Product updateProduct(int productID, Product detachedPOJO);
	
	String deleteProduct(int productID);

	List<ProductDto> getAllProductsByCategoryName(String categoryName);

	List<ProductDto> getAllProductsByVendorName(String vendorName);
	
	List<ProductDto> getAllProductsByVendorEmail(String email);

	List<ProductDto> getProductByCategory(Category category);
}
