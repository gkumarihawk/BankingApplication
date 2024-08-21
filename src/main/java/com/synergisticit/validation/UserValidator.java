package com.synergisticit.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.synergisticit.domain.User;

@Component
public class UserValidator implements Validator {
	

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return User.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		User u = (User)target;
		
		if(u.getUsername().length()<2) {
			errors.rejectValue("username", "username.length", "Name should not be less than 2 characters.");
		}
		
		
		if(u.getPassword() == null) {
			errors.rejectValue("password", "password.value", "Password should not be null");
		}
		
		if(u.getEmail() == null) {
			errors.rejectValue("email", "email.value", "Email should not be null");
		}
		
		if(u.getRoles().isEmpty()) {
			errors.rejectValue("roles", "role.empty", "The roles should not be empty");
		}
		
		/*ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "username.empty", "Name should not be empty.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userId", "designation.empty", "Designation should not be empty.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "password.empty", "Email should not be empty.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "email.empty", "Please enter mobile number.");*/

	}

}
