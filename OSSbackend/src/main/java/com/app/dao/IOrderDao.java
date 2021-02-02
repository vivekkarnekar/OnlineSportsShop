package com.app.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.app.pojos.Customer;
import com.app.pojos.Order;

public interface IOrderDao extends JpaRepository<Order, Integer> {

	Order findById(int id);

	@Query(value = "SELECT * FROM ORDERS o WHERE o.customer_id =:id", nativeQuery = true)
	List<Order> findByCustomerId(@Param("id") Customer id);
}