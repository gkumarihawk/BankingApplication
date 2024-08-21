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

import com.synergisticit.domain.User;
import com.synergisticit.repository.UserRepository;

@Controller
public class UserPagedController {
	
	@Autowired UserRepository userRepository;
	
	@RequestMapping("/findTheUsers")
	public ResponseEntity<List<User>> findTheUsers(@RequestParam int pageNo, @RequestParam int pageSize, @RequestParam String sortedBy){
		Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortedBy).descending());
		Page<User> pagedUser = userRepository.findAll(pageable);
		List<User> users = pagedUser.getContent();
		return new ResponseEntity<List<User>>(users, HttpStatus.OK);
	}
	
	@RequestMapping(value="pagedUser")
	public String findThePagedUser(@RequestParam int pageNo, @RequestParam int pageSize, @RequestParam String sortedBy, Model model) {
		Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortedBy));
		Page<User> pageOfUsers = userRepository.findAll(pageable);
		List<User> users = pageOfUsers.getContent();
		model.addAttribute("users", users);
		model.addAttribute("totalPages", pageOfUsers.getTotalPages());
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("sortedBy", sortedBy);
		return "pagedUser";
	}
	

}



/*
 * @Controller
public class EmployeePagedController {
	@Autowired EmployeeRepository employeeRepository;

	/*
	http://localhost:8081/robinApp/findTheEmployees?pageNo=1&pageSize=5&sortedBy=designation
	http://localhost:8081/robinApp/findTheEmployees?pageNo=0&pageSize=8&sortedBy=designation
	
	@RequestMapping("findTheEmployees")
	public ResponseEntity<List<Employee>> findTheEmployees(@RequestParam int pageNo, 
			@RequestParam int pageSize, @RequestParam String sortedBy){	
		//Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortedBy));
		Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortedBy).descending());
		Page<Employee> pagedEmployee = employeeRepository.findAll(pageable);
		List<Employee> employees = pagedEmployee.getContent();
		return new ResponseEntity<List<Employee>>(employees, HttpStatus.OK);
	}
	
	//http://localhost:8081/robinApp/pagedEmployee?pageNo=1&pageSize=5&sortedBy=name
	@RequestMapping(value="pagedEmployee")
	public String findThePagedEmployee(@RequestParam int pageNo, @RequestParam int pageSize, 
			@RequestParam String sortedBy, Model model) {
		System.out.println("pagedEmployee...");
		Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortedBy));
		//Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortedBy).descending());
		Page<Employee> pageOfEmployees =employeeRepository.findAll(pageable);
		List<Employee> employees = pageOfEmployees.getContent();
		model.addAttribute("employees", employees);
		model.addAttribute("totalPages", pageOfEmployees.getTotalPages());
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("sortedBy", sortedBy);
		return "pagedEmployee";
	}
}

 */

