package com.app.pojos;

import java.io.Serializable;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "categories")
@NamedQuery(name = "Category.findAll", query = "SELECT c FROM Category c")
public class Category implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "category_id")
	private Integer id;

	@Column(length = 45, nullable = false, name = "CategoryName")
	@JsonProperty(value = "categoryName")
	@NotBlank(message = "Category name must be supplied ")
	private String name;

	@Column(name = "AsscociatedSport", length = 45, nullable = false)
	@NotBlank(message = "Asscociated Sport must be supplied ")
	private String asscociatedSport;

	//bi-directional many-to-one association to Product
	//@OneToMany(mappedBy="category",cascade=CascadeType.ALL)
	//@JsonManagedReference(value="product_category")
	//private List<Product> products;

	public Category() {
		super();
	}

	public Category(Integer id, String name, String asscociatedSport, String brand) {
		super();
		this.id = id;
		this.name = name;
		this.asscociatedSport = asscociatedSport;

		// this.products = products;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAsscociatedSport() {
		return this.asscociatedSport;
	}

	public void setAsscociatedSport(String asscociatedSport) {
		this.asscociatedSport = asscociatedSport;
	}

	@Override
	public String toString() {
		return "Category [categoryId=" + id + ", categoryName=" + name + ", asscociatedSport=" + asscociatedSport
				+ "]";
	}

}