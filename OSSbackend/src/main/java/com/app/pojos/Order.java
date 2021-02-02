package com.app.pojos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="orders")
@NamedQuery(name="Order.findAll", query="SELECT o FROM Order o")
public class Order implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "order_id")
	private Integer id;
	
	@ManyToMany(fetch = FetchType.LAZY,cascade = { CascadeType.REFRESH, CascadeType.MERGE}) //mandatory : if not supplied hibernate throws MappingExc.
	@JoinTable(name="order_product",joinColumns = @JoinColumn(name="order_id"),inverseJoinColumns = @JoinColumn(name="product_id"))
	@JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private List<Product> products=new ArrayList<>();

	@OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	@JoinColumn(name="customer_id",nullable=false)
	//@JsonBackReference(value = "customer_order")
	//@JsonIgnoreProperties("orders")
	private Customer customer;

	public Order() {
		super();
	}

	public Order(Integer id, List<Product> products, Customer customer) {
		super();
		this.id = id;
		this.products = products;
		this.customer = customer;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", customer=" + customer + "]";
	}

}











