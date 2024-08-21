package com.synergisticit.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.synergisticit.domain.User;
import com.synergisticit.service.UserService;
import com.synergisticit.validation.UserValidator;

import jakarta.validation.Valid;

@RestController
@RequestMapping("user")
public class UserRestController {
	
	@Autowired UserService userService;
	
	@Autowired UserValidator userValidator;
	
		@PostMapping(value="save")
		public ResponseEntity<?> saveUser(@Valid @RequestBody User user, BindingResult br){
			userValidator.validate(user, br);
			HttpHeaders headers = new HttpHeaders();
			if(userService.existsById(user.getUserId()) || br.hasFieldErrors()) {
				StringBuilder sb = new StringBuilder();
				if(br.hasFieldErrors()) {
					List<FieldError> fieldErrors = br.getFieldErrors();
					for(FieldError fe : fieldErrors) {
						sb.append(fe.getField()+": ")
						.append(fe.getDefaultMessage() +"\n");
					}
					System.out.println(sb.toString());
					headers.add("Error Count", String.valueOf(fieldErrors.size()));
					return new ResponseEntity<String>(sb.toString(), headers, HttpStatus.CONFLICT);
				}else {
					return new ResponseEntity<String>("User with id "+ user.getUserId() + " already exists.",  HttpStatus.CREATED);
				}
				
			}else {
				User retrivedUser = userService.save(user);
				return new ResponseEntity<User>(retrivedUser, HttpStatus.CREATED);
			}
		};
		
		@RequestMapping(value="update")
		public ResponseEntity<?> updateUser(@RequestBody User user){
			User retrivedUser = userService.findById(user.getUserId());
			user.getRoles().forEach(r -> System.out.println(r.getRoleName()));
			HttpHeaders headers = new HttpHeaders();
			if(retrivedUser != null) {
				User updatedUser = userService.save(user);
				headers.add("UPDATED - ", String.valueOf(user.getUserId()));
				return new ResponseEntity<User>(updatedUser, HttpStatus.OK);
			}else {
				headers.add("NOT FOUND - ", String.valueOf(user.getUserId()));
				return new ResponseEntity<String>("No user with id="+user.getUserId(), HttpStatus.BAD_REQUEST);
			}
			
		};
		
		@GetMapping(value="findAll")
		public ResponseEntity<List<User>> findAllUsers(){
			return new ResponseEntity<List<User>>(userService.findAll(), HttpStatus.FOUND);
		}

		@GetMapping(value="find")
		public ResponseEntity<?> findUserById(@RequestParam Long userId){
			User  foundUser = userService.findById(userId);
			if( foundUser != null) {
				return new ResponseEntity<User>( foundUser ,  HttpStatus.FOUND);
			}else {
				return new ResponseEntity<String>("No User with id "+userId+"." ,HttpStatus.FOUND);	
			}
		}
		
		@RequestMapping(value="delete")
		public ResponseEntity<String> deleteUserById(@RequestParam Long userId){
			System.out.println("deleteUserById("+userId+")");
			userService.deleteById(userId);
			return new ResponseEntity<String>(HttpStatus.GONE);
		}

}
