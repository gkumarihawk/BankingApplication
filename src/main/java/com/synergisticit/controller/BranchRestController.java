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
import com.synergisticit.domain.Branch;
import com.synergisticit.service.AccountService;
import com.synergisticit.service.BranchService;
import com.synergisticit.validation.AccountValidator;
import com.synergisticit.validation.BranchValidator;

import jakarta.validation.Valid;

@RestController
@RequestMapping("branch")
public class BranchRestController {
	
@Autowired BranchService branchService;
	
	@Autowired BranchValidator branchValidator;
	
	@PostMapping(value="save")
	public ResponseEntity<?> saveBranch(@Valid @RequestBody Branch branch, BindingResult br){
		branchValidator.validate(branch, br);
		HttpHeaders headers = new HttpHeaders();
		if(branchService.existsById(branch.getBranchId()) || br.hasFieldErrors()) {
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
				return new ResponseEntity<String>("Branch with id "+ branch.getBranchId() + " already exists.",  HttpStatus.CREATED);
			}
			
		}else {
			Branch retrivedBranch = branchService.saveBranch(branch);
			return new ResponseEntity<Branch>(retrivedBranch, HttpStatus.CREATED);
		}
	};
	
	@RequestMapping(value="update")
	public ResponseEntity<?> updateBranch(@RequestBody Branch branch){
		Branch retrivedBranch = branchService.findById(branch.getBranchId());
		//account.getRoles().forEach(r -> System.out.println(r.getRoleName()));
		HttpHeaders headers = new HttpHeaders();
		if(retrivedBranch != null) {
			Branch updatedBranch = branchService.saveBranch(branch);
			headers.add("UPDATED - ", String.valueOf(branch.getBranchId()));
			return new ResponseEntity<Branch>(updatedBranch, HttpStatus.OK);
		}else {
			headers.add("NOT FOUND - ", String.valueOf(branch.getBranchId()));
			return new ResponseEntity<String>("No branch with id="+branch.getBranchId(), HttpStatus.BAD_REQUEST);
		}
		
	};
	
	@GetMapping(value="findAll")
	public ResponseEntity<List<Branch>> findAllBranchs(){
		return new ResponseEntity<List<Branch>>(branchService.findAll(), HttpStatus.FOUND);
	}

	@GetMapping(value="find")
	public ResponseEntity<?> findBranchById(@RequestParam Long branchId){
		Branch foundBranch = branchService.findById(branchId);
		if( foundBranch != null) {
			return new ResponseEntity<Branch>( foundBranch ,  HttpStatus.FOUND);
		}else {
			return new ResponseEntity<String>("No Branch with id "+branchId+"." ,HttpStatus.FOUND);	
		}
	}
	
	@RequestMapping(value="delete")
	public ResponseEntity<String> deleteBranchById(@RequestParam Long branchId){
		System.out.println("deleteBranchById("+branchId+")");
		branchService.deleteById(branchId);
		return new ResponseEntity<String>(HttpStatus.GONE);
	}
	

}
