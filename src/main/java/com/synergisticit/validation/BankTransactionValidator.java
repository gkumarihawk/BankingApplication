package com.synergisticit.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.synergisticit.domain.BankTransaction;
import com.synergisticit.domain.Branch;

@Component
public class BankTransactionValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return BankTransaction.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		BankTransaction bt = (BankTransaction)target;
		
		if(bt.getBankTransactionAmount() ==  null) {
			errors.rejectValue("bankTransactionAmount", "bankTransactionAmount.value", "Please Enter the Amount");
		}
		
		/*
		 * if(bt.getBankTransactionFromAccount()==null) {
		 * errors.rejectValue("bankTransactionFromAccount",
		 * "bankTransactionFromAccount.value", "Please enter the Account Id"); }
		 * 
		 * if(bt.getBankTransactionToAccount()==null) {
		 * errors.rejectValue("bankTransactionToAccount",
		 * "bankTransactionToAccount.value", "Please enter the Account Id"); }
		 */
		
		if(bt.getBankTransactionType()==null) {
			errors.rejectValue("bankTransactionType", "bankTransactionType.value", "Select the Transaction Type.");
		}
		
		if(bt.getComments().isEmpty()) {
			errors.rejectValue("comments", "comments.value", "Please leave some comments");
		}
		
	}

}
