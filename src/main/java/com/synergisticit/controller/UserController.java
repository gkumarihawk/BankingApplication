package com.synergisticit.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.synergisticit.domain.Role;
import com.synergisticit.domain.User;
import com.synergisticit.service.RoleService;
import com.synergisticit.service.UserService;
import com.synergisticit.validation.UserValidator;
import org.springframework.security.core.context.SecurityContextHolder;

@Controller
public class UserController {
		
	@Autowired UserService userService;
	@Autowired RoleService roleService;
	
	@Autowired UserValidator userValidator;
	
	@RequestMapping("userForm")
	public String userForm(User user, Model model, Authentication authentication) {
		
		//Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		model.addAttribute("users", userService.findAll());
		model.addAttribute("roles", roleService.findAll());
		
		//String username = auth.getName(); // Get the username of the currently logged-in user
		//User currentUser = userService.findByUsername(username); // Retrieve the user details by username
		//model.addAttribute("currentUser", currentUser); // Add the current user object to the model
		    
		    // Add only the roles associated with the current user to the model
		//model.addAttribute("roles", currentUser.getRoles());
		return "userForm";
	}
	
	/*@RequestMapping("saveUser")
	public String savesTheUser(@Validated @ModelAttribute User user, Model model) {
		userValidator.validateObject(user, br);
		ModelAndView mav = new ModelAndView("userForm");
		System.out.println("br.hasErrors(): "+br.hasErrors());
		userService.save(user);
		model.addAttribute("users", userService.findAll());
		model.addAttribute("roles", roleService.findAll());
		//return "userForm";
		return "redirect:userForm";
	}*/
	
	@RequestMapping("saveUser")
	public ModelAndView savesTheUser(@Validated @ModelAttribute User user, BindingResult br) {
		userValidator.validate(user, br);
		ModelAndView mav = new ModelAndView("userForm");
		System.out.println("br.hasErrors(): "+br.hasErrors());
		if(br.hasErrors()) {
			mav.addObject("hasErrors", br.hasErrors());
			List<User> users = userService.findAll();
			List<Role> roles = roleService.findAll();
			mav.addObject("users", users);
			mav.addObject("roles", roles);
			return mav;
		}else {
			
			userService.save(user);
			return new ModelAndView("redirect:userForm");
		}
	}
	
	/*@RequestMapping("saveEmployee")
	public ModelAndView saveTheEmployee(@Validated @ModelAttribute Employee e, BindingResult br) {
		employeeValidator.validate(e, br);
		
		ModelAndView mav = new ModelAndView("employeeForm");
		System.out.println("br.hasErrors():" +br.hasErrors());
		if(br.hasErrors()) {
			mav.addObject("hasErrors", br.hasErrors());
			List<Employee> employees = employeeService.findAll();
			mav.addObject("employees", employees);
			return mav;
		}else{
		employeeService.save(e);
		return new ModelAndView("redirect:employeeForm");
		}
		
	}*/
	
	@RequestMapping("updateUser")
	public String forUpdatingTheUser(User user, Model model) {
		
		User retrievedUser = userService.findById(user.getUserId());
		List<Role> retrievedRoles = retrievedUser.getRoles();
		model.addAttribute("users", userService.findAll());
		model.addAttribute("roles", roleService.findAll());
		model.addAttribute("u", retrievedUser);
		model.addAttribute("retrievedRoles", retrievedRoles);
		return "userForm";
	}
	
	@RequestMapping("deleteUser")
	public String deletesTheUser(User user) {
		userService.deleteById(user.getUserId());
		return "redirect:userForm";
	}
	
	//findAll?sortBy=userId
		@RequestMapping("findAllUsers")
		public String findAll(User user, @RequestParam String sortBy, Model model) {
			model.addAttribute("users", userService.findAll(sortBy));
			return "userForm";
		}

}
