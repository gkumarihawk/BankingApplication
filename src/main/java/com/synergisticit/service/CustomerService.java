package com.synergisticit.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.synergisticit.domain.Customer;
import com.synergisticit.domain.User;



public interface CustomerService {
	
	Customer saveCustomer(Customer customer);
	Customer findById(long customerId);
	List<Customer> findAll();
	void deleteById(long customerId);
	boolean existsById(long customerId);
	Long getNextCustomerId();
	
	List<Customer> findAll(String sortBy);

}
