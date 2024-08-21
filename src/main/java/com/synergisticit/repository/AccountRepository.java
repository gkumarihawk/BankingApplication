package com.synergisticit.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.synergisticit.domain.Account;
import com.synergisticit.domain.BankTransaction;
import com.synergisticit.domain.User;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long>{

	@Query(value = "SELECT COALESCE(MAX(accountId), 0) + 1 FROM Account", nativeQuery = true)
    Long getNextAccountId();
	
	@Query("SELECT a FROM Account a WHERE a.accountNumber = :accountNumber")
    Account findByAccountNumber(Long accountNumber);
	
	Page<Account> findAll(Pageable pageable);
	
	List<Account> findAll(Sort sort);
	
}
