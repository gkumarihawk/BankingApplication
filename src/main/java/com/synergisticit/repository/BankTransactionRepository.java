package com.synergisticit.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.synergisticit.domain.BankTransaction;
import com.synergisticit.domain.Customer;
import com.synergisticit.domain.User;

@Repository
public interface BankTransactionRepository extends JpaRepository<BankTransaction, Long>{
	
	@Query(value = "SELECT COALESCE(MAX(bankTransactionId), 0) + 1 FROM BankTransaction", nativeQuery = true)
    Long getNextBankTransactionId();
	
	List<BankTransaction> findByBankTransactionDateTimeBetween(LocalDateTime startDate, LocalDateTime endDate);
	
	Page<BankTransaction> findAll(Pageable pageable);
	
	List<BankTransaction> findAll(Sort sort);

}
