package com.synergisticit.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.synergisticit.domain.Account;
import com.synergisticit.domain.AccountType;
import com.synergisticit.domain.Branch;
import com.synergisticit.domain.Customer;
import com.synergisticit.domain.Gender;
import com.synergisticit.domain.User;
import com.synergisticit.service.AccountService;
import com.synergisticit.service.BranchService;
import com.synergisticit.service.CustomerService;
import com.synergisticit.validation.AccountValidator;

@Controller
public class AccountController {
	
	@Autowired AccountService accountService;
	@Autowired BranchService branchService;
	@Autowired CustomerService customerService;
	
	@Autowired AccountValidator accountValidator;
	
	@RequestMapping("accountForm")
	public ModelAndView accountForm(Account account) {
		return getModelAndView(); 
	}
	
	@RequestMapping("saveAccount")
	public ModelAndView savesTheAccount(@Validated @ModelAttribute Account account, BindingResult br) {
		accountValidator.validate(account, br);
		ModelAndView mav = getModelAndView(); 
		System.out.println("br.hasErrors(): "+br.hasErrors());
		if(br.hasErrors()) {
			mav.addObject("hasErrors", br.hasErrors());
			List<Customer> customers = customerService.findAll();
			List<Account> accounts = accountService.findAll();
			List<Branch> branches = branchService.findAll();
			mav.addObject("customers", customers);
			mav.addObject("accounts", accounts);
			mav.addObject("branches", branches);
		
			return mav;
		}else {
			
			accountService.saveAccount(account);
			return new ModelAndView("redirect:accountForm");
		}
	}
	
	@RequestMapping("updateAccount")
	public String forUpdatingTheAccount(Account account, Model model) {
		//System.out.println("forUpdatingTheAccount");
		Account retrievedAccount = accountService.findById(account.getAccountId());
		Customer retrievedCustomer = customerService.findById(retrievedAccount.getAccountCustomer().getCustomerId());
		AccountType retrievedAccountType =  retrievedAccount.getAccountType();
		Branch retrievedBranch = branchService.findById(retrievedAccount.getAccountBranch().getBranchId());
		
		model.addAttribute("accounts", accountService.findAll());
		model.addAttribute("retrievedCustomer", customerService.findAll());
		model.addAttribute("customer", retrievedCustomer);
		model.addAttribute("a", retrievedAccount);
		model.addAttribute("accountTypes", AccountType.values());
		model.addAttribute("retrievedBranch", branchService.findAll());
		model.addAttribute("branches", retrievedBranch);
		model.addAttribute("retrievedAccountType", retrievedAccountType);
		return "accountForm";
	}
		
	@RequestMapping("deleteAccount")
	public String deletesTheAccount(Account account) {
		//ModelAndView mav = new ModelAndView("redirect:customerForm");
		accountService.deleteById(account.getAccountId());
		return "redirect:accountForm";
	}
	
	private ModelAndView getModelAndView() {
		ModelAndView mav = new ModelAndView("accountForm");
		List<Account> accounts = accountService.findAll();
		
		mav.addObject("accounts", accounts);
		mav.addObject("accountTypes", AccountType.values());
		mav.addObject("branches", branchService.findAll());
		mav.addObject("customers", customerService.findAll());
		 mav.addObject("nextAccountId", accountService.getNextAccountId());
	
		return mav;
	}
	
	@RequestMapping("findAllAccounts")
	public String findAll(Account account, @RequestParam String sortBy, Model model) {
		model.addAttribute("accounts", accountService.findAll(sortBy));
		return "accountForm";
	}

}
