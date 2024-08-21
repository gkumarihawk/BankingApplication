package com.synergisticit.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.synergisticit.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	
	User findByUsername(String name);
	
	Page<User> findAll(Pageable pageable);
	
	List<User> findAll(Sort sort);

}
