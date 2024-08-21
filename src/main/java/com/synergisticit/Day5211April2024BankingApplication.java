package com.synergisticit;

import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.synergisticit.domain.Role;
import com.synergisticit.service.RoleService;

@SpringBootApplication
public class Day5211April2024BankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(Day5211April2024BankingApplication.class, args);
		
		
		/*ApplicationContext context = new ClassPathXmlApplicationContext("db-beans.xml");
        
        int beanCount = context.getBeanDefinitionCount();
        System.out.println("beanCount: "+beanCount);
        
        String[] beanNames = context.getBeanDefinitionNames();
       
		
		  for(String beanName : beanNames) { 
			  System.out.println(beanName+": "+context.getBean(beanName)); 
		  
		  }*/
		  
		 /* RoleService  roleService = (RoleService)context.getBean("roleService");
		  
		  Role r1 = new Role(8L, "Doctor");

		  
		  System.out.println("\nFinding all roles...");
		  List<Role> roles = roleService.findAll();
		  
		 for(Role r : roles) {
			 System.out.println(r);
		 }
		 
		 /*System.out.println("\nUpdating an employee by empId...");
		 Employee e2 = new Employee(2, "Annie", "Sr. Programmer", 18000);
		 employeeService.update(e2);
		 
		 
		 System.out.println("\nFinding an employee by empId...");
		 Employee e =  employeeService.findById(2);
		 System.out.println(e);
		 
		 System.out.println("\nDeleting an employee by empId...");
		 employeeService.deleteById(5);
		 
		 System.out.println("\nFinding all employees after deleting 1 employee record...");
		 employees = employeeService.findAll();
		 for(Employee theEmployee : employees) {
			 System.out.println(theEmployee);
		 }*/
     
	}

}
