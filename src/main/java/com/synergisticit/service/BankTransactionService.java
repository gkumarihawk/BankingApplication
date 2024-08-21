package com.synergisticit.service;

import java.time.LocalDateTime;
import java.util.List;

import com.synergisticit.domain.BankTransaction;
import com.synergisticit.domain.Branch;
import com.synergisticit.domain.User;

public interface BankTransactionService {
	
	BankTransaction saveBankTransaction(BankTransaction bankTransaction);
	BankTransaction findById(long bankTransactionId);
	List<BankTransaction> findAll();
	void deleteById(long bankTransactionId);
	boolean existsById(long bankTransactionId);
	Long getNextBankTransactionId();
	List<BankTransaction> findByDateRange(LocalDateTime startDate, LocalDateTime endDate);
	
	List<BankTransaction> findAll(String sortBy);

}
