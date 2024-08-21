package com.synergisticit.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.synergisticit.domain.Role;
import com.synergisticit.service.RoleService;

@RestController
@RequestMapping("role")
public class RoleRestController {
	
	@Autowired RoleService roleService;
	
	@RequestMapping("saveRole")
	public ResponseEntity<?> savesTheRole(@RequestParam Long roleId, @RequestParam String name){
		Role role = new Role();
		role.setRoleId(roleId);
		role.setRoleName(name);
		roleService.save(role);
		return new  ResponseEntity<String>(role.getRoleName(), HttpStatus.FOUND);
	}
	
	//http://localhost:8080/role/findAll
	//@RequestMapping("findAll")
	@GetMapping(value="findAll")
	public ResponseEntity<List<Role>> findAllRoles(){
		System.out.println("RoleController..");
		return new ResponseEntity<List<Role>>(roleService.findAll(),  HttpStatus.FOUND);
	}
	
	@GetMapping(value="find")
	public ResponseEntity<Role> findRoleById(@RequestParam Long roleId){
		return new ResponseEntity<Role>(roleService.findById(roleId),  HttpStatus.FOUND);
	}
	
	

}
