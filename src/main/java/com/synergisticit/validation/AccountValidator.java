package com.synergisticit.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.synergisticit.domain.Account;
import com.synergisticit.domain.Customer;

@Component
public class AccountValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return Account.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		
		Account a = (Account)target;
		
		if(a.getAccountType() ==  null) {
			errors.rejectValue("accountType", "accountType.value", "Please select an Account Type");
		}
		
		if(a.getAccountHolder().isEmpty()) {
			errors.rejectValue("accountHolder", "accountHolder.value", "Please enter the Account Holder");
		}
		
		if(a.getAccountDateOpened()==null) {
			errors.rejectValue("accountDateOpened", "accountDateOpened.value", "Select the date.");
		}
		
		if(a.getAccountBalance()==null) {
			errors.rejectValue("accountBalance", "accountBalance.value", "Please enter the Account Balance");
		}
		
		if(a.getAccountNumber()==null) {
			errors.rejectValue("accountNumber", "accountNumber.value", "Please enter the Account Number");
		}
		
		if(a.getAccountBranch().getBranchId()==null) {
			errors.rejectValue("accountBranch", "accountBranch.value", "Please enter the BranchId");
		}
		
		
		
	}

}
