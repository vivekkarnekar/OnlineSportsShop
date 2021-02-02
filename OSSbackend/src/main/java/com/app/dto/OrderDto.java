package com.app.dto;

import java.util.List;

import com.app.pojos.Customer;
import com.app.pojos.Product;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class OrderDto {

	private Integer id;
	
	private List<Product> products;
	
	@JsonIgnore
	private Customer customer;
	
	private Integer customerId;

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

}











