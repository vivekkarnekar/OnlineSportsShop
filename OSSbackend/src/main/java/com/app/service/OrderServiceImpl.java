package com.app.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.custom_excs.OrderNotFoundException;
import com.app.dao.ICustomerDao;
import com.app.dao.IOrderDao;
import com.app.dto.OrderDto;
import com.app.pojos.Customer;
import com.app.pojos.Order;
import com.app.pojos.Product;

@Service
@Transactional
public class OrderServiceImpl implements IOrderService {
	// dependency
	@Autowired
	private IOrderDao dao;
	@Autowired
	private ICustomerDao customerDao;

	private OrderDto mapToDto(Order order) {
		OrderDto orderDto = new OrderDto();
		BeanUtils.copyProperties(order, orderDto);
		orderDto.setCustomerId(order.getCustomer().getId());
		return orderDto;
	}

	@Override
	public List<OrderDto> getAllOrders() {
		System.out.println("dao imple class " + dao.getClass().getName());
		List<Order> orderEntities = dao.findAll();
		List<OrderDto> orders = orderEntities.stream().map(ord -> mapToDto(ord)).collect(Collectors.toList());
		return orders;
	}

	@Override
	public OrderDto getOrderById(int id) {
		Order optionalOrder = dao.findById(id);
		OrderDto order = mapToDto(optionalOrder);
		return order;
	}

	@Override
	public Integer addOrder(OrderDto orderDto) {
		Order order = new Order();
		BeanUtils.copyProperties(orderDto, order);
		// OrderItem orderItem = orderItemDao.findById(productDto.getOrderItemId()).get();
		// product.setOrderItem(orderItem);
		Customer customer = customerDao.findById(orderDto.getCustomerId()).get();
		order.setCustomer(customer);

		Order savedOrder = dao.save(order);
		return savedOrder.getId();
	}

	@Override
	public OrderDto updateOrder(int orderID, Order existingOrder) {
		// chk if Order exists : findById
		Order updatedOrder = dao.findById(orderID);
		if (updatedOrder != null) {
			Order order = dao.save(updatedOrder);
			OrderDto finalOrder = new OrderDto();
			BeanUtils.copyProperties(order, finalOrder);
			finalOrder.setCustomerId(order.getCustomer().getId());
			return finalOrder;
		}
		throw new OrderNotFoundException("Invalid Order ID");
	}

	@Override
	public String deleteOrder(int orderID) {
		dao.deleteById(orderID);
		return "Order with ID " + orderID + " deleted";
	}
	
	@Override
	public double calculateTotalAmount(int id) {
		Order o =dao.findById(id);
		int totalPrice=0;
		if(o!=null)
		{
			for (Product order : o.getProducts()) {
				totalPrice+=order.getUnitPrice();
			}
			return totalPrice;
			
		}
		return 0.0;
	}

}
