package com.app.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.pojos.Category;

public interface ICategoryDao extends JpaRepository<Category, Integer>{

	Category findByName(String cName);
	
	Category findById(int id);

}