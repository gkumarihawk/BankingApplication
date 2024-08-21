package com.synergisticit.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.synergisticit.domain.Account;
import com.synergisticit.domain.BankTransaction;
import com.synergisticit.domain.User;
import com.synergisticit.repository.BankTransactionRepository;

@Service
public class BankTransactionServiceImpl implements BankTransactionService{
	
	@Autowired BankTransactionRepository bankTransactionRepository;

	@Override
	public BankTransaction saveBankTransaction(BankTransaction bankTransaction) {
		
		return bankTransactionRepository.save(bankTransaction);
	}

	@Override
	public BankTransaction findById(long bankTransactionId) {
		Optional<BankTransaction> optBankTransaction = bankTransactionRepository.findById(bankTransactionId);
		if(optBankTransaction.isPresent()) {
			return optBankTransaction.get();
		}
		return null;
	}

	@Override
	public List<BankTransaction> findAll() {
		// TODO Auto-generated method stub
		return bankTransactionRepository.findAll();
	}

	@Override
	public void deleteById(long bankTransactionId) {
		// TODO Auto-generated method stub
		bankTransactionRepository.deleteById(bankTransactionId);
		
	}

	@Override
	public boolean existsById(long bankTransactionId) {
		// TODO Auto-generated method stub
		return bankTransactionRepository.existsById(bankTransactionId);
	}
	
	public Long getNextBankTransactionId() {
        return bankTransactionRepository.getNextBankTransactionId();
    }

	
	 @Override public List<BankTransaction> findByDateRange(LocalDateTime startDate, LocalDateTime endDate) { 
		 return bankTransactionRepository.findByBankTransactionDateTimeBetween(startDate, endDate); 
	 }
	 
	 @Override
		public List<BankTransaction> findAll(String sortBy) {
			// TODO Auto-generated method stub
			return bankTransactionRepository.findAll(Sort.by(sortBy));
		}
	 

}
