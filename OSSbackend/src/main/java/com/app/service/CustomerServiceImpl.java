package com.app.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.custom_excs.OrderNotFoundException;
import com.app.dao.ICustomerDao;
import com.app.dto.CustomerDto;
import com.app.pojos.Customer;

@Service
@Transactional
public class CustomerServiceImpl implements ICustomerService {
	// dependency
	@Autowired
	private ICustomerDao dao;

	@Override
	public List<Customer> getAllCustomers() {
		System.out.println("dao imple class " + dao.getClass().getName());
		return dao.findAll();
	}

	@Override
	public ResponseEntity<Customer> findByEmailAndPassword(String email, String password) {

		Customer authCustomer = dao.findByEmailAndPassword(email, password);

		return new ResponseEntity<Customer>(authCustomer, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Customer> findByEmail(String email) {
		dao.findByEmail(email);
		return null;
	}

	@Override
	public Customer getCustomerById(int id) {
		return dao.findById(id);
	}

	@Override
	public Customer addCustomer(Customer transientPOJO) {
		// TODO Auto-generated method stub
		return dao.save(transientPOJO);
	}

	@Override
	public CustomerDto updateCustomer(int customerID, Customer existingCustomer) {
		// chk if Order exists : findById
		Customer updatedCustomer = dao.findById(customerID);
		if (updatedCustomer != null) {
			updatedCustomer.setAddress(existingCustomer.getAddress());
			updatedCustomer.setContactNumber(existingCustomer.getContactNumber());
			updatedCustomer.setPassword(existingCustomer.getPassword());
			updatedCustomer.setName(existingCustomer.getName());
			Customer customer = dao.save(updatedCustomer);
			CustomerDto finalCustomer = new CustomerDto();
			BeanUtils.copyProperties(customer, finalCustomer);
			return finalCustomer;
		}
		throw new OrderNotFoundException("Invalid Order ID");
	}

	@Override
	public ResponseEntity<?> deleteCustomer(int customerID) {
		try {
			dao.deleteById(customerID);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (RuntimeException e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<?> deleteAllCustomers() {

		try {
			dao.deleteAll();
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (RuntimeException e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

}
