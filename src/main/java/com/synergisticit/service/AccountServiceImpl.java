package com.synergisticit.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.synergisticit.domain.Account;
import com.synergisticit.domain.Branch;
import com.synergisticit.domain.User;
import com.synergisticit.repository.AccountRepository;

@Service
public class AccountServiceImpl implements AccountService{
	
	@Autowired AccountRepository accountRepository;

	@Override
	public Account saveAccount(Account account) {
		
		return accountRepository.save(account);
	}

	@Override
	public Account findById(long accountId) {
		
		Optional<Account> optAccount = accountRepository.findById(accountId);
		if(optAccount.isPresent()) {
			return optAccount.get();
		}
		return null;
	}

	@Override
	public List<Account> findAll() {
		
		return accountRepository.findAll();
	}

	@Override
	public void deleteById(long accountId) {
		
		accountRepository.deleteById(accountId);
		
	}

	@Override
	public boolean existsById(long accountId) {
		
		return accountRepository.existsById(accountId);
	}
	
	public Long getNextAccountId() {
        return accountRepository.getNextAccountId();
    }

	@Override
	public Account findByAccountNumber(Long accountNumber) {
		// TODO Auto-generated method stub
		return accountRepository.findByAccountNumber(accountNumber);
	}
	
	@Override
	public List<Account> findAll(String sortBy) {
		// TODO Auto-generated method stub
		return accountRepository.findAll(Sort.by(sortBy));
	}

	

}
