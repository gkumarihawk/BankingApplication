package com.synergisticit.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.synergisticit.domain.Branch;
import com.synergisticit.domain.Customer;
import com.synergisticit.domain.User;
import com.synergisticit.repository.CustomerRepository;

@Service
public class CustomerServiceImpl implements CustomerService {
	
	@Autowired CustomerRepository customerRepository;

	@Override
	public Customer saveCustomer(Customer customer) {
		
		return customerRepository.save(customer);
	}

	@Override
	public Customer findById(long customerId) {
		
		Optional<Customer> optCustomer = customerRepository.findById(customerId);
		if(optCustomer.isPresent()) {
			return optCustomer.get();
		}
		return null;
	}

	@Override
	public List<Customer> findAll() {
		
		return customerRepository.findAll();
	}

	@Override
	public void deleteById(long customerId) {
		customerRepository.deleteById(customerId);

	}

	@Override
	public boolean existsById(long customerId) {
		
		return customerRepository.existsById(customerId);
	}
	
	public Long getNextCustomerId() {
        return customerRepository.getNextCustomerId();
    }
	
	@Override
	public List<Customer> findAll(String sortBy) {
		// TODO Auto-generated method stub
		return customerRepository.findAll(Sort.by(sortBy));
	}

}
