package com.synergisticit.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.synergisticit.domain.Account;
import com.synergisticit.domain.Customer;
import com.synergisticit.service.AccountService;
import com.synergisticit.service.CustomerService;
import com.synergisticit.validation.AccountValidator;
import com.synergisticit.validation.CustomerValidator;

import jakarta.validation.Valid;

@RestController
@RequestMapping("customer")
public class CustomerRestController {
	
@Autowired CustomerService customerService;
	
	@Autowired CustomerValidator customerValidator;
	
	@PostMapping(value="save")
	public ResponseEntity<?> saveCustomer(@Valid @RequestBody Customer customer, BindingResult br){
		customerValidator.validate(customer, br);
		HttpHeaders headers = new HttpHeaders();
		if(customerService.existsById(customer.getCustomerId()) || br.hasFieldErrors()) {
			StringBuilder sb = new StringBuilder();
			if(br.hasFieldErrors()) {
				List<FieldError> fieldErrors = br.getFieldErrors();
				for(FieldError fe : fieldErrors) {
					sb.append(fe.getField()+": ")
					.append(fe.getDefaultMessage() +"\n");
				}
				System.out.println(sb.toString());
				headers.add("Error Count", String.valueOf(fieldErrors.size()));
				return new ResponseEntity<String>(sb.toString(), headers, HttpStatus.CONFLICT);
			}else {
				return new ResponseEntity<String>("Customer with id "+ customer.getCustomerId() + " already exists.",  HttpStatus.CREATED);
			}
			
		}else {
			Customer retrivedCustomer = customerService.saveCustomer(customer);
			return new ResponseEntity<Customer>(retrivedCustomer, HttpStatus.CREATED);
		}
	};
	
	@RequestMapping(value="update")
	public ResponseEntity<?> updateCustomer(@RequestBody Customer customer){
		Customer retrivedCustomer = customerService.findById(customer.getCustomerId());
		//account.getRoles().forEach(r -> System.out.println(r.getRoleName()));
		HttpHeaders headers = new HttpHeaders();
		if(retrivedCustomer != null) {
			Customer updatedCustomer = customerService.saveCustomer(customer);
			headers.add("UPDATED - ", String.valueOf(customer.getCustomerId()));
			return new ResponseEntity<Customer>(updatedCustomer, HttpStatus.OK);
		}else {
			headers.add("NOT FOUND - ", String.valueOf(customer.getCustomerId()));
			return new ResponseEntity<String>("No customer with id="+customer.getCustomerId(), HttpStatus.BAD_REQUEST);
		}
		
	};
	
	@GetMapping(value="findAll")
	public ResponseEntity<List<Customer>> findAllCustomers(){
		return new ResponseEntity<List<Customer>>(customerService.findAll(), HttpStatus.FOUND);
	}

	@GetMapping(value="find")
	public ResponseEntity<?> findCustomerById(@RequestParam Long customerId){
		Customer foundCustomer = customerService.findById(customerId);
		if( foundCustomer != null) {
			return new ResponseEntity<Customer>( foundCustomer ,  HttpStatus.FOUND);
		}else {
			return new ResponseEntity<String>("No Customer with id "+customerId+"." ,HttpStatus.FOUND);	
		}
	}
	
	@RequestMapping(value="delete")
	public ResponseEntity<String> deleteCustomerById(@RequestParam Long customerId){
		System.out.println("deleteAccountById("+customerId+")");
		customerService.deleteById(customerId);
		return new ResponseEntity<String>(HttpStatus.GONE);
	}

}
