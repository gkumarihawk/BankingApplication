/*package com.synergisticit.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SecurityController {
	
	//@RequestMapping("/")
	public ResponseEntity<String> m1(){
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String personLoggedIn = auth.getName();
		
		StringBuilder sb =  new StringBuilder();
		sb.append("Hello "+personLoggedIn+", ")
		.append("<br>Your roles are: "+ auth.getAuthorities())
		.append("<br><a href=\"logout\">logout</a>");
		
		return new ResponseEntity<String>(sb.toString(), HttpStatus.OK);
	}

}*/
