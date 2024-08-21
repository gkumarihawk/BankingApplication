package com.synergisticit.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.synergisticit.domain.Customer;
import com.synergisticit.domain.User;

@Component
public class CustomerValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return Customer.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		
		Customer c = (Customer)target;
		
		if(c.getCustomerName().length()<2) {
			errors.rejectValue("customerName", "customerName.length", "Name should not be less than 2 characters.");
		}
				
		if(c.getCustomerMobileNo() == null) {
			errors.rejectValue("customerMobileNo", "customerMobileNo.value", "Email should not be null");
		}
		
		if(c.getCustomerDOB()==null) {
			errors.rejectValue("customerDOB", "customerDOB", "Select the date of birth.");
		}
		
	}

	

}
