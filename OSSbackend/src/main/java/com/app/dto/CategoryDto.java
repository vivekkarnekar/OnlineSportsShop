package com.app.dto;

public class CategoryDto {

	private Integer id;

	private String name;

	private String asscociatedSport;

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
	
}