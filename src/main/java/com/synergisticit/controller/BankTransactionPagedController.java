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

import com.synergisticit.domain.BankTransaction;
import com.synergisticit.domain.Branch;
import com.synergisticit.repository.BankTransactionRepository;
import com.synergisticit.repository.BranchRepository;

@Controller
public class BankTransactionPagedController {
	
@Autowired BankTransactionRepository bankTransactionRepository;
	
	@RequestMapping("/findTheBankTransactions")
	public ResponseEntity<List<BankTransaction>> findTheUsers(@RequestParam int pageNo, @RequestParam int pageSize, @RequestParam String sortedBy){
		Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortedBy).descending());
		Page<BankTransaction> pagedBankTransaction = bankTransactionRepository.findAll(pageable);
		List<BankTransaction> bankTransactions = pagedBankTransaction.getContent();
		return new ResponseEntity<List<BankTransaction>>(bankTransactions, HttpStatus.OK);
	}
	
	@RequestMapping(value="pagedBankTransaction")
	public String findThePagedUser(@RequestParam int pageNo, @RequestParam int pageSize, @RequestParam String sortedBy, Model model) {
		Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortedBy));
		Page<BankTransaction> pageOfBankTransactions = bankTransactionRepository.findAll(pageable);
		List<BankTransaction> bankTransactions = pageOfBankTransactions.getContent();
		model.addAttribute("bankTransactions", bankTransactions);
		model.addAttribute("totalPages", pageOfBankTransactions.getTotalPages());
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("sortedBy", sortedBy);
		return "pagedBankTransaction";
	}

}
