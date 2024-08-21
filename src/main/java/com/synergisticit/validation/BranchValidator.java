package com.synergisticit.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.synergisticit.domain.Branch;
import com.synergisticit.domain.Customer;

@Component
public class BranchValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return Branch.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		
		Branch b = (Branch)target;
		
		if(b.getBranchName().length()<2) {
			errors.rejectValue("branchName", "branchName.length", "Name should not be less than 2 characters.");
		}
		
		if(b.getBranchAddress() ==  null) {
			errors.rejectValue("branchAddress", "branchAddress.value", "Please Enter the address");
		}
		
		
		
	}

}
