package com.synergisticit.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.synergisticit.domain.Role;
import com.synergisticit.service.RoleService;

@Controller
public class RoleController {
	
	@Autowired RoleService roleService;
	
	@Autowired private JdbcTemplate jdbcTemplate;
	
	@RequestMapping("roleForm")
	public String displayRoleForm(Role role, Model model){
		System.out.println("RoleController - roleForm.....");
		model.addAttribute("roles", roleService.findAll());
		return "roleForm";
	}
	@RequestMapping("saveRole")
	public String savesRole(@ModelAttribute Role role){
		roleService.save(role);
		return "redirect:roleForm";
	}

	//deleteRole
	@RequestMapping("deleteRole")
	public String deletesTheRole(@ModelAttribute Role role){
		roleService.deleteById(role.getRoleId());
		return "redirect:roleForm";
	}
	
	//updateRole
	@RequestMapping("updateRole")
	public String updateRole(@ModelAttribute Role role, Model model){
		Role r = roleService.findById(role.getRoleId());
		System.out.println("r: "+r.getRoleName());
		model.addAttribute("r", r);
		model.addAttribute("roles", roleService.findAll());
		return "roleForm";
	}
	
	//findAll?sortBy=userId
	@RequestMapping("findAllRoles")
	public String findAll(Role role, @RequestParam String sortBy, Model model) {
		model.addAttribute("roles", roleService.findAll(sortBy));
		return "roleForm";
			}
	
}
