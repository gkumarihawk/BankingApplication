package com.synergisticit.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.synergisticit.domain.Branch;
import com.synergisticit.domain.User;

@Repository
public interface BranchRepository extends JpaRepository<Branch, Long>{
	
	@Query(value = "SELECT COALESCE(MAX(branchId), 0) + 1 FROM Branch", nativeQuery = true)
    Long getNextBranchId();
	
	Page<Branch> findAll(Pageable pageable);
	
	List<Branch> findAll(Sort sort);

}
