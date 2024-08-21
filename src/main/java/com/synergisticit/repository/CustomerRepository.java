package com.synergisticit.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.synergisticit.domain.Branch;
import com.synergisticit.domain.Customer;
import com.synergisticit.domain.User;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>{
	
	@Query(value = "SELECT COALESCE(MAX(customerId), 0) + 1 FROM Customer", nativeQuery = true)
    Long getNextCustomerId();
	
	Page<Customer> findAll(Pageable pageable);
	
	List<Customer> findAll(Sort sort);

}
