package com.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.dao.IOrderDao;
import com.app.dto.OrderDto;
import com.app.pojos.Customer;
import com.app.pojos.Order;
import com.app.service.IOrderService;

@RestController
@RequestMapping("/order")
@CrossOrigin(origins = "http://localhost:4200")
public class OrderController {
	// dependency
	@Autowired
	private IOrderService service;

	@Autowired
	private IOrderDao dao;

	public OrderController() {
		System.out.println("in ctor of " + getClass().getName());
	}

	// RESTful end point or API end point or API provider
	// for retrieve the details of one manager
	@GetMapping
	public ResponseEntity<?> listAllOrders() {
		System.out.println("in list all orders");
		// invoke service layer's method : controller --> service impl (p) --->JPA
		// repo's impl class(SC)
		List<OrderDto> order = service.getAllOrders();
		if (order.isEmpty())
			// empty list : set sts code : HTTP 204 (no contents)
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		// in case of non empty list : OK, send the list
		return ResponseEntity.ok(order);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getOrderDetailsById(@PathVariable int id) {
		System.out.println("in get Order details " + id);
		// invoke service method
		OrderDto orderDetails = service.getOrderById(id);
		// valid name : HTTP 200 , marshalled product details
		if (orderDetails != null)
			return new ResponseEntity<>(orderDetails, HttpStatus.OK);
		// in case of invalid name : HTTP 404
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@GetMapping("/customer/{id}")
	public ResponseEntity<?> getordersByCustomerId(@PathVariable(value = "id") Customer id) {
		List<Order> orders = dao.findByCustomerId(id);
		if (org.springframework.util.CollectionUtils.isEmpty(orders)) {
			// empty list : set sts code : HTTP 204 (no contents)
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		// in case of non empty list : OK, send the list
		return ResponseEntity.ok(orders);
	}

	// req handling method to create a new order : post
	@PostMapping
	public ResponseEntity<?> addOrder(@RequestBody OrderDto newOrder) {
		System.out.println("in add Order " + newOrder);
		try {
			Order order = new Order();
			order.setProducts(newOrder.getProducts());
			order.setCustomer(newOrder.getCustomer());
			dao.save(order);
			Integer savedOrder = service.addOrder(newOrder);
			return new ResponseEntity<>(savedOrder, HttpStatus.OK);

		} catch (RuntimeException e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// req handling method to update existing order
	@PutMapping("/{orderID}")
	public ResponseEntity<?> updateOrderDetails(@PathVariable int orderID, @RequestBody Order updatedOrder) {
		System.out.println("in update " + orderID + " " + updatedOrder);
		try {
			OrderDto updatedDetails = service.updateOrder(orderID, updatedOrder);
			return new ResponseEntity<>(updatedDetails, HttpStatus.OK);
		} catch (RuntimeException e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("total/{orderId}")
	@CrossOrigin(origins = "http://localhost:4200")
	public ResponseEntity<?> getTotalAmount(@PathVariable int orderId)
	{
		try {
			double totalPrice=service.calculateTotalAmount(orderId);
			return new ResponseEntity<>(totalPrice, HttpStatus.OK);
		}catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		 
	}

	@DeleteMapping("/{orderID}")
	public String deleteOrder(@PathVariable int orderID) {
		System.out.println("in delete order " + orderID);
		return service.deleteOrder(orderID);

	}

}
