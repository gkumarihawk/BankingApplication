package com.synergisticit.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.synergisticit.domain.Customer;
import com.synergisticit.domain.Role;
import com.synergisticit.repository.CustomerRepository;
import com.synergisticit.service.CustomerService;

@Controller
public class CustomerPagedController {
	
	@Autowired CustomerRepository customerRepository;
	
	@RequestMapping("/findTheCustomers")
	public ResponseEntity<List<Customer>> findTheUsers(@RequestParam int pageNo, @RequestParam int pageSize, @RequestParam String sortedBy){
		Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortedBy).descending());
		Page<Customer> pagedCustomer = customerRepository.findAll(pageable);
		List<Customer> customers = pagedCustomer.getContent();
		return new ResponseEntity<List<Customer>>(customers, HttpStatus.OK);
	}
	
	@RequestMapping(value="pagedCustomer")
	public String findThePagedUser(@RequestParam int pageNo, @RequestParam int pageSize, @RequestParam String sortedBy, Model model) {
		Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortedBy));
		Page<Customer> pageOfCustomers = customerRepository.findAll(pageable);
		List<Customer> customers = pageOfCustomers.getContent();
		model.addAttribute("customers", customers);
		model.addAttribute("totalPages", pageOfCustomers.getTotalPages());
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("sortedBy", sortedBy);
		return "pagedCustomer";
	}

}
