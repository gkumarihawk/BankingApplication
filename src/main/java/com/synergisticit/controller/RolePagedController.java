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

import com.synergisticit.domain.Role;
import com.synergisticit.domain.User;
import com.synergisticit.repository.RoleRepository;
import com.synergisticit.repository.UserRepository;

@Controller
public class RolePagedController {
	
	@Autowired RoleRepository roleRepository;
	
	@RequestMapping("/findTheRoles")
	public ResponseEntity<List<Role>> findTheUsers(@RequestParam int pageNo, @RequestParam int pageSize, @RequestParam String sortedBy){
		Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortedBy).descending());
		Page<Role> pagedRole = roleRepository.findAll(pageable);
		List<Role> roles = pagedRole.getContent();
		return new ResponseEntity<List<Role>>(roles, HttpStatus.OK);
	}
	
	@RequestMapping(value="pagedRole")
	public String findThePagedUser(@RequestParam int pageNo, @RequestParam int pageSize, @RequestParam String sortedBy, Model model) {
		Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortedBy));
		Page<Role> pageOfRoles = roleRepository.findAll(pageable);
		List<Role> roles = pageOfRoles.getContent();
		model.addAttribute("roles", roles);
		model.addAttribute("totalPages", pageOfRoles.getTotalPages());
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("sortedBy", sortedBy);
		return "pagedRole";
	}
	
	

}
