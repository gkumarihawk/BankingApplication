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

import com.synergisticit.domain.Account;
import com.synergisticit.domain.Branch;
import com.synergisticit.repository.AccountRepository;
import com.synergisticit.repository.BranchRepository;

@Controller
public class AccountPagedController {
	
@Autowired AccountRepository accountRepository;
	
	@RequestMapping("/findTheAccounts")
	public ResponseEntity<List<Account>> findTheUsers(@RequestParam int pageNo, @RequestParam int pageSize, @RequestParam String sortedBy){
		Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortedBy).descending());
		Page<Account> pagedAccount = accountRepository.findAll(pageable);
		List<Account> accounts = pagedAccount.getContent();
		return new ResponseEntity<List<Account>>(accounts, HttpStatus.OK);
	}
	
	@RequestMapping(value="pagedAccount")
	public String findThePagedUser(@RequestParam int pageNo, @RequestParam int pageSize, @RequestParam String sortedBy, Model model) {
		Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortedBy));
		Page<Account> pageOfAccounts = accountRepository.findAll(pageable);
		List<Account> accounts = pageOfAccounts.getContent();
		model.addAttribute("accounts", accounts);
		model.addAttribute("totalPages", pageOfAccounts.getTotalPages());
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("sortedBy", sortedBy);
		return "pagedAccount";
	}

}
