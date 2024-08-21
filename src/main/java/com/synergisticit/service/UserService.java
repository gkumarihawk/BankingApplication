package com.synergisticit.service;

import java.util.List;

import com.synergisticit.domain.User;

public interface UserService {
	
	User save(User user);
	User findById(long userId);
	
	List<User> findAll();
	
	void deleteById(long userId);
	boolean existsById(long userId);
	
	User findByUsername(String name);
	
	//14-Mar-2024
	List<User> findAll(String sortBy);
	
	User findByName(String name);

}
