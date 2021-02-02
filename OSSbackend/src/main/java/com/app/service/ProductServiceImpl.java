package com.app.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.custom_excs.ProductNotFoundException;
import com.app.dao.ICategoryDao;
import com.app.dao.IProductDao;
import com.app.dao.IVendorDao;
import com.app.dto.ProductDto;
import com.app.pojos.Category;
import com.app.pojos.Product;
import com.app.pojos.Vendor;

@Service
@Transactional
public class ProductServiceImpl implements IProductService {
	// dependency
	@Autowired
	private IProductDao dao;

	//@Autowired
	//private IOrderItemDao orderItemDao;

	@Autowired
	private ICategoryDao categoryDao;

	@Autowired
	private IVendorDao vendorDao;

	private ProductDto mapToDto(Product product) {
		ProductDto productDto = new ProductDto();
		BeanUtils.copyProperties(product, productDto);
		productDto.setCategoryId(product.getCategory().getId());
		// productDto.setOrderItemId(product.getOrderItem().getId());
		productDto.setVendorId(product.getVendor().getId());
		productDto.setVendorName(product.getVendor().getName());
		productDto.setCategoryName(product.getCategory().getName());
		return productDto;
	}

	@Override
	public List<ProductDto> getAllProducts() {
		System.out.println("dao imple class " + dao.getClass().getName());
		List<Product> productEntities = dao.findAll();
		List<ProductDto> prods = productEntities.stream().map(prod -> mapToDto(prod)).collect(Collectors.toList());
		return prods;
	}

	@Override
	public ProductDto getProductById(int id) {
		Product optionalProduct = dao.findById(id);
		ProductDto prod = mapToDto(optionalProduct);
		return prod;
	}

	@Override
	public ProductDto getProductByName(String pName) {
		Product optionalProduct = dao.findByName(pName);
		ProductDto prod = mapToDto(optionalProduct);
		if (prod!=null) {
			return prod;
		}
		return null;
	}

	@Override
	public List<ProductDto> getAllProductsByCategoryName(String categoryName) {
		List<Product> productEntities = dao.findByCategoryName(categoryName);
		List<ProductDto> prods = productEntities.stream().map(prod -> mapToDto(prod)).collect(Collectors.toList());

		return prods;
	}

	@Override
	public List<ProductDto> getAllProductsByVendorName(String vendorName) {
		List<Product> productEntities = dao.findByVendorName(vendorName);
		List<ProductDto> prods = productEntities.stream().map(prod -> mapToDto(prod)).collect(Collectors.toList());

		return prods;
	}

	@Override
	public List<ProductDto> getAllProductsByVendorEmail(String email) {
		List<Product> productEntities = dao.findByVendorEmail(email);
		List<ProductDto> prods = productEntities.stream().map(prod -> mapToDto(prod)).collect(Collectors.toList());

		return prods;
	}

	@Override
	public Integer addProduct(ProductDto productDto) {
		Product product = new Product();
		BeanUtils.copyProperties(productDto, product);
		Vendor vendor = vendorDao.findById(productDto.getVendorId()).get();
		product.setVendor(vendor);
		Category category = categoryDao.findById(productDto.getCategoryId()).get();
		product.setCategory(category);
		Product savedProduct = dao.save(product);
		return savedProduct.getId();
	}

	@Override
	public Product updateProduct(int productID, Product existingProduct) {
		Product updatedProduct = dao.findById(productID);
		if (updatedProduct != null) {
			updatedProduct.setUnitPrice(existingProduct.getUnitPrice());
			updatedProduct.setQuantity(existingProduct.getQuantity());
			return dao.save(updatedProduct);
		}
		throw new ProductNotFoundException("Invalid Product ID");
	}
	
	@Override
	public List<ProductDto> getProductByCategory(Category category) {		
		List<Product> productEntities = dao.findByCategory(category);
		List<ProductDto> prods = productEntities.stream().map(prod -> mapToDto(prod)).collect(Collectors.toList());
		
		return prods;
	}

	@Override
	public String deleteProduct(int productID) {
		dao.deleteById(productID);
		return "Product with ID " + productID + " deleted";
	}

}
