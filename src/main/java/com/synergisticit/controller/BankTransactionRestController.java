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
import com.synergisticit.domain.BankTransaction;
import com.synergisticit.service.BankTransactionService;
import com.synergisticit.validation.BankTransactionValidator;

import jakarta.validation.Valid;

@RestController
@RequestMapping("bankTransaction")
public class BankTransactionRestController {
	
	@Autowired BankTransactionService bankTransactionService;
	
	@Autowired BankTransactionValidator bankTransactionValidator;
	
	
	@PostMapping(value="save")
	public ResponseEntity<?> saveBankTransaction(@Valid @RequestBody BankTransaction bankTransaction, BindingResult br){
		bankTransactionValidator.validate(bankTransaction, br);
		HttpHeaders headers = new HttpHeaders();
		if(bankTransactionService.existsById(bankTransaction.getBankTransactionId()) || br.hasFieldErrors()) {
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
				return new ResponseEntity<String>("bankTransaction with id "+ bankTransaction.getBankTransactionId() + " already exists.",  HttpStatus.CREATED);
			}
			
		}else {
			BankTransaction retrivedBankTransaction = bankTransactionService.saveBankTransaction(bankTransaction);
			return new ResponseEntity<BankTransaction>(retrivedBankTransaction, HttpStatus.CREATED);
		}
	};
	
	@RequestMapping(value="update")
	public ResponseEntity<?> updateBankTransaction(@RequestBody BankTransaction bankTransaction){
		BankTransaction retrivedBankTransaction = bankTransactionService.findById(bankTransaction.getBankTransactionId());
		//account.getRoles().forEach(r -> System.out.println(r.getRoleName()));
		HttpHeaders headers = new HttpHeaders();
		if(retrivedBankTransaction != null) {
			BankTransaction updatedBankTransaction = bankTransactionService.saveBankTransaction(bankTransaction);
			headers.add("UPDATED - ", String.valueOf(bankTransaction.getBankTransactionId()));
			return new ResponseEntity<BankTransaction>(updatedBankTransaction, HttpStatus.OK);
		}else {
			headers.add("NOT FOUND - ", String.valueOf(bankTransaction.getBankTransactionId()));
			return new ResponseEntity<String>("No bankTransaction with id="+bankTransaction.getBankTransactionId(), HttpStatus.BAD_REQUEST);
		}
		
	};
	
	@GetMapping(value="findAll")
	public ResponseEntity<List<BankTransaction>> findAllBankTransactions(){
		return new ResponseEntity<List<BankTransaction>>(bankTransactionService.findAll(), HttpStatus.FOUND);
	}

	@GetMapping(value="find")
	public ResponseEntity<?> findAccountById(@RequestParam Long bankTransactionId){
		BankTransaction  foundBankTransaction = bankTransactionService.findById(bankTransactionId);
		if( foundBankTransaction != null) {
			return new ResponseEntity<BankTransaction>( foundBankTransaction ,  HttpStatus.FOUND);
		}else {
			return new ResponseEntity<String>("No bankTransaction with id "+bankTransactionId+"." ,HttpStatus.FOUND);	
		}
	}
	
	@RequestMapping(value="delete")
	public ResponseEntity<String> deleteBankTransactionById(@RequestParam Long bankTransactionId){
		System.out.println("deleteBankTransactionById("+bankTransactionId+")");
		bankTransactionService.deleteById(bankTransactionId);
		return new ResponseEntity<String>(HttpStatus.GONE);
	}

}
