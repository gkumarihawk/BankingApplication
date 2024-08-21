package com.synergisticit.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.synergisticit.domain.Account;
import com.synergisticit.domain.Branch;
import com.synergisticit.domain.Customer;
import com.synergisticit.domain.Gender;
import com.synergisticit.domain.Role;
import com.synergisticit.domain.User;
import com.synergisticit.service.AccountService;
import com.synergisticit.service.BranchService;
import com.synergisticit.validation.BranchValidator;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;

@Controller
public class BranchController {
	
	@Autowired BranchService branchService;
	@Autowired AccountService accountService;
	
	@Autowired BranchValidator branchValidator;
	
	@RequestMapping("branchForm")
	public String branchform(Branch branch, Model model) {
		model.addAttribute("branches", branchService.findAll());
		model.addAttribute("accounts", accountService.findAll());
		model.addAttribute("nextBranchId", branchService.getNextBranchId());
		return "branchForm";
	}
	
	@RequestMapping("saveBranch")
	public ModelAndView savesTheBranch(@Validated @ModelAttribute Branch branch, BindingResult br) {
		branchValidator.validate(branch, br);
		ModelAndView mav = new ModelAndView("branchForm");
		System.out.println("br.hasErrors(): "+br.hasErrors());
		if(br.hasErrors()) {
			mav.addObject("hasErrors", br.hasErrors());
			List<Branch> branches = branchService.findAll();
			List<Account> accounts = accountService.findAll();
			mav.addObject("branches", branches);
			mav.addObject("accounts", accounts);
			return mav;
		}else {
			
			branchService.saveBranch(branch);
			return new ModelAndView("redirect:branchForm");
		}
	}

	@RequestMapping("updateBranch")
	public String forUpdatingTheBranch(Branch branch, Model model) {
	
		Branch retrievedBranch = branchService.findById(branch.getBranchId());
		List<Account> retrievedAccount = retrievedBranch.getBranchAccounts();
		model.addAttribute("branches", branchService.findAll());
		model.addAttribute("accounts", accountService.findAll());
		model.addAttribute("b", retrievedBranch);
		model.addAttribute("retrievedAccount", retrievedAccount);
		return "branchForm";
	}

	@RequestMapping("deleteBranch")
	public String deletesTheBranch(Branch branch) {
		branchService.deleteById(branch.getBranchId());
		return "redirect:branchForm";
	}
	
	@RequestMapping("findAllBranches")
	public String findAll(Branch branch, @RequestParam String sortBy, Model model) {
		model.addAttribute("branches", branchService.findAll(sortBy));
		return "branchForm";
	}
	
	
}