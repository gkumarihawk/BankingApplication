package com.synergisticit.service;

import java.util.List;
import java.util.Optional;

import com.synergisticit.domain.Account;
import com.synergisticit.domain.BankTransaction;
import com.synergisticit.domain.Branch;
import com.synergisticit.domain.User;

public interface AccountService {
	
	Account saveAccount(Account account);
	Account findById(long accountId);
	List<Account> findAll();
	void deleteById(long accountId);
	boolean existsById(long accountId);
	Long getNextAccountId();
	Account findByAccountNumber(Long accountNumber);
	
	List<Account> findAll(String sortBy);


}
