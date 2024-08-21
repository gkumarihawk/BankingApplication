package com.synergisticit.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.synergisticit.domain.Branch;
import com.synergisticit.domain.User;


public interface BranchService {
	
	Branch saveBranch(Branch branch);
	Branch findById(long branchId);
	List<Branch> findAll();
	void deleteById(long branchId);
	boolean existsById(long branchId);
	Long getNextBranchId();
	
	List<Branch> findAll(String sortBy);
	

}
