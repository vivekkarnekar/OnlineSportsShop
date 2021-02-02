package com.app.service;

import java.util.List;

import com.app.dto.OrderDto;
import com.app.pojos.Order;

public interface IOrderService {

	List<OrderDto> getAllOrders();

	Integer addOrder(OrderDto transientPOJO);

	OrderDto updateOrder(int orderID, Order detachedPOJO);

	String deleteOrder(int orderID);

	OrderDto getOrderById(int id);
	
	double calculateTotalAmount(int id);

}
