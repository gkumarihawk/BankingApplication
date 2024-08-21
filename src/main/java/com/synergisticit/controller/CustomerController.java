package com.synergisticit.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.synergisticit.domain.Account;
import com.synergisticit.domain.Customer;
import com.synergisticit.domain.Gender;
import com.synergisticit.domain.Role;
import com.synergisticit.domain.User;
import com.synergisticit.service.AccountService;
import com.synergisticit.service.CustomerService;
import com.synergisticit.service.UserService;
import com.synergisticit.validation.CustomerValidator;
import com.synergisticit.validation.UserValidator;

@Controller
public class CustomerController {
	
	@Autowired CustomerService customerService;
	@Autowired AccountService accountService;
	@Autowired UserService userService;
	
	@Autowired CustomerValidator customerValidator;
	
	
	/*
	 * @RequestMapping("customerForm") public ModelAndView customerForm(Customer
	 * customer) { return getModelAndView(); }
	 */
	
	@GetMapping("/customerForm")
    public ModelAndView showCustomerForm() {
        ModelAndView mav = new ModelAndView("customerForm");
        mav.addObject("customer", new Customer());
        mav.addObject("genders", Gender.values());
        mav.addObject("users", userService.findAll());
        mav.addObject("nextCustomerId", customerService.getNextCustomerId());
        return mav;
    }
	
	@RequestMapping("saveCustomer")
	public ModelAndView savesTheCustomer(@Validated @ModelAttribute Customer customer, BindingResult br) {
		customerValidator.validate(customer, br);
		ModelAndView mav = getModelAndView(); 
		System.out.println("br.hasErrors(): "+br.hasErrors());
		if(br.hasErrors()) {
			mav.addObject("hasErrors", br.hasErrors());
			List<Customer> customers = customerService.findAll();
			List<Account> accounts = accountService.findAll();
			List<User> users = userService.findAll();
			mav.addObject("customers", customers);
			mav.addObject("accounts", accounts);
			mav.addObject("users", users);
		
			return mav;
		}else {
			
			customerService.saveCustomer(customer);
			return new ModelAndView("redirect:customerForm");
		}
	}
	
	@RequestMapping("updateCustomer")
	public String forUpdatingTheCustomer(Customer customer, Model model) {
		System.out.println("forUpdatingTheCustomer");
		Customer retrievedCustomer = customerService.findById(customer.getCustomerId());
		User retrievedUser = userService.findById(retrievedCustomer.getUser().getUserId());
		Gender retrievedGender =  retrievedCustomer.getCustomerGender();
		model.addAttribute("customers", customerService.findAll());
		model.addAttribute("users", userService.findAll());
		model.addAttribute("user", retrievedUser);
		model.addAttribute("c", retrievedCustomer);
		model.addAttribute("genders", Gender.values());
		model.addAttribute("retrievedGender", retrievedGender);
		return "customerForm";
	}
	
	
	
	@RequestMapping("deleteCustomer")
	public String deletesTheCustomer(Customer customer) {
		//ModelAndView mav = new ModelAndView("redirect:customerForm");
		customerService.deleteById(customer.getCustomerId());
		return "redirect:customerForm";
	}
	
	private ModelAndView getModelAndView() {
		ModelAndView mav = new ModelAndView("customerForm");
		List<Customer> customers = customerService.findAll();
		mav.addObject("customers", customers);
		mav.addObject("genders", Gender.values());
		mav.addObject("users", userService.findAll());
	
		return mav;
	}
	
	@GetMapping("/getUserInfo/{userId}")
	@ResponseBody
	public Customer getUserInfo(@PathVariable Long userId) {
	    // Retrieve the customer based on the user ID
	    Customer customer = customerService.findById(userId);
	    
	    return customer;
	}
	
	@RequestMapping("findAllCustomers")
	public String findAll(Customer customer, @RequestParam String sortBy, Model model) {
		model.addAttribute("customers", customerService.findAll(sortBy));
		return "customerForm";
	}

}


