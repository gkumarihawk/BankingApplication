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

import com.synergisticit.domain.Branch;
import com.synergisticit.domain.Customer;
import com.synergisticit.repository.BranchRepository;
import com.synergisticit.repository.CustomerRepository;

@Controller
public class BranchPagedController {
	
@Autowired BranchRepository branchRepository;
	
	@RequestMapping("/findTheBranches")
	public ResponseEntity<List<Branch>> findTheUsers(@RequestParam int pageNo, @RequestParam int pageSize, @RequestParam String sortedBy){
		Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortedBy).descending());
		Page<Branch> pagedBranch = branchRepository.findAll(pageable);
		List<Branch> branches = pagedBranch.getContent();
		return new ResponseEntity<List<Branch>>(branches, HttpStatus.OK);
	}
	
	@RequestMapping(value="pagedBranch")
	public String findThePagedUser(@RequestParam int pageNo, @RequestParam int pageSize, @RequestParam String sortedBy, Model model) {
		Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortedBy));
		Page<Branch> pageOfBranches = branchRepository.findAll(pageable);
		List<Branch> branches = pageOfBranches.getContent();
		model.addAttribute("branches", branches);
		model.addAttribute("totalPages", pageOfBranches.getTotalPages());
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("sortedBy", sortedBy);
		return "pagedBranch";
	}

}
