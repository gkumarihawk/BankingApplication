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
import com.synergisticit.domain.User;
import com.synergisticit.service.AccountService;
import com.synergisticit.validation.AccountValidator;

import jakarta.validation.Valid;

@RestController
@RequestMapping("account")
public class AccountRestController {
	
	@Autowired AccountService accountService;
	
	@Autowired AccountValidator accountValidator;
	
	@PostMapping(value="save")
	public ResponseEntity<?> saveAccount(@Valid @RequestBody Account account, BindingResult br){
		accountValidator.validate(account, br);
		HttpHeaders headers = new HttpHeaders();
		if(accountService.existsById(account.getAccountId()) || br.hasFieldErrors()) {
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
				return new ResponseEntity<String>("Account with id "+ account.getAccountId() + " already exists.",  HttpStatus.CREATED);
			}
			
		}else {
			Account retrivedAccount = accountService.saveAccount(account);
			return new ResponseEntity<Account>(retrivedAccount, HttpStatus.CREATED);
		}
	};
	
	@RequestMapping(value="update")
	public ResponseEntity<?> updateAccount(@RequestBody Account account){
		Account retrivedAccount = accountService.findById(account.getAccountId());
		//account.getRoles().forEach(r -> System.out.println(r.getRoleName()));
		HttpHeaders headers = new HttpHeaders();
		if(retrivedAccount != null) {
			Account updatedAccount = accountService.saveAccount(account);
			headers.add("UPDATED - ", String.valueOf(account.getAccountId()));
			return new ResponseEntity<Account>(updatedAccount, HttpStatus.OK);
		}else {
			headers.add("NOT FOUND - ", String.valueOf(account.getAccountId()));
			return new ResponseEntity<String>("No account with id="+account.getAccountId(), HttpStatus.BAD_REQUEST);
		}
		
	};
	
	@GetMapping(value="findAll")
	public ResponseEntity<List<Account>> findAllAccounts(){
		return new ResponseEntity<List<Account>>(accountService.findAll(), HttpStatus.FOUND);
	}

	@GetMapping(value="find")
	public ResponseEntity<?> findAccountById(@RequestParam Long accountId){
		Account  foundAccount = accountService.findById(accountId);
		if( foundAccount != null) {
			return new ResponseEntity<Account>( foundAccount ,  HttpStatus.FOUND);
		}else {
			return new ResponseEntity<String>("No User with id "+accountId+"." ,HttpStatus.FOUND);	
		}
	}
	
	@RequestMapping(value="delete")
	public ResponseEntity<String> deleteAccountById(@RequestParam Long accountId){
		System.out.println("deleteAccountById("+accountId+")");
		accountService.deleteById(accountId);
		return new ResponseEntity<String>(HttpStatus.GONE);
	}
	

}
