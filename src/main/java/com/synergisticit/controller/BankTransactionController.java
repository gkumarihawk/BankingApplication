package com.synergisticit.controller;


import java.util.List;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.synergisticit.domain.Account;
import com.synergisticit.domain.AccountType;
import com.synergisticit.domain.BankTransaction;
import com.synergisticit.domain.BankTransactionType;
import com.synergisticit.domain.Branch;
import com.synergisticit.domain.Customer;
import com.synergisticit.domain.Gender;
import com.synergisticit.service.AccountService;
import com.synergisticit.service.BankTransactionService;
import com.synergisticit.validation.BankTransactionValidator;

import jakarta.servlet.http.HttpServletRequest;


@Controller
public class BankTransactionController {
	
	@Autowired BankTransactionService bankTransactionService;
	@Autowired AccountService accountService;
	@Autowired BankTransactionValidator bankTransactionValidator;
	
	@RequestMapping("bankTransactionForm")
	public ModelAndView showBankTransaction(BankTransaction bankTransaction) {
		ModelAndView mav = new ModelAndView("bankTransactionForm");
		List<Account> accounts = accountService.findAll();
		mav.addObject("bankTransactions", bankTransactionService.findAll());
        mav.addObject("bankTransaction", new BankTransaction());
        mav.addObject("bankTransactionTypes", BankTransactionType.values());
        //if (accounts != null && !accounts.isEmpty()) {
        //    mav.addObject("bankTransactionFromAccount", accounts);
        //    mav.addObject("bankTransactionToAccount", accounts);
       // }
        mav.addObject("nextBankTransactionId", bankTransactionService.getNextBankTransactionId());
        return mav;
	}
	
	@RequestMapping("saveBankTransaction")
	public ModelAndView savesTheBankTransaction(@Validated @ModelAttribute BankTransaction bankTransaction, BindingResult br, HttpServletRequest req) {
		System.out.println("req "+ req.getAttribute("bankTransactionFromAccount"));
	    bankTransactionValidator.validate(bankTransaction, br);
	    ModelAndView mav = new ModelAndView("bankTransactionForm");
	    if (br.hasErrors()) {
	        mav.addObject("hasErrors", br.hasErrors());
	        List<BankTransaction> bankTransactions = bankTransactionService.findAll();
	        List<Account> accounts = accountService.findAll();
	        mav.addObject("bankTransactions", bankTransactions);
	        mav.addObject("accounts", accounts);
	        return mav;
	    } else {
	        // Handle transaction based on transaction type
	        switch (bankTransaction.getBankTransactionType()) {
	            case DEPOSIT:
	                // Update the account balance for deposit transaction
	                Account depositAccount = accountService.findById(bankTransaction.getBankTransactionToAccount().getAccountId());
	                if (depositAccount != null) {
	                    double depositCurrentBalance = depositAccount.getAccountBalance();
	                    double depositNewBalance = depositCurrentBalance + bankTransaction.getBankTransactionAmount();
	                    depositAccount.setAccountBalance(depositNewBalance);
	                    accountService.saveAccount(depositAccount); // Update the account with the new balance
	                    System.out.println("Deposit to account ID: " + depositAccount.getAccountId());
	                } else {
	                    System.out.println("Deposit account is null");
	                }
	                break;
	            case WITHDRAW:
	                // Update the account balance for withdraw transaction
	                Account withdrawAccount = accountService.findById(bankTransaction.getBankTransactionFromAccount().getAccountId());
	                if (withdrawAccount != null) {
	                    double withdrawCurrentBalance = withdrawAccount.getAccountBalance();
	                    double withdrawNewBalance = withdrawCurrentBalance - bankTransaction.getBankTransactionAmount();
	                    withdrawAccount.setAccountBalance(withdrawNewBalance);
	                    accountService.saveAccount(withdrawAccount); // Update the account with the new balance
	                    System.out.println("Withdraw from account ID: " + withdrawAccount.getAccountId());
	                } else {
	                    System.out.println("Withdraw account is null");
	                }
	                break;
	            case TRANSFER:
	                // Update the account balances for transfer transaction
	            	
	            	System.out.println("Here is some info "+bankTransaction.getBankTransactionToAccount());//prints null??????
	            	System.out.println("Here is some info "+bankTransaction.getBankTransactionFromAccount());
	                Account transferFromAccount = accountService.findById(bankTransaction.getBankTransactionFromAccount().getAccountId());
	                //System.out.println("Hey I have reached" + transferFromAccount);
	                Account transferToAccount = accountService.findById(bankTransaction.getBankTransactionToAccount().getAccountId());
	                if (transferFromAccount != null && transferToAccount != null) {
	                    double transferAmount = bankTransaction.getBankTransactionAmount();
	                    double transferNewFromBalance = transferFromAccount.getAccountBalance() - transferAmount;
	                    double transferNewToBalance = transferToAccount.getAccountBalance() + transferAmount;
	                    transferFromAccount.setAccountBalance(transferNewFromBalance);
	                    transferToAccount.setAccountBalance(transferNewToBalance);
	                    accountService.saveAccount(transferFromAccount); // Update the source account balance
	                    accountService.saveAccount(transferToAccount); // Update the destination account balance
	                    System.out.println("Transfer from account ID: " + transferFromAccount.getAccountId());
	                    System.out.println("Transfer to account ID: " + transferToAccount.getAccountId());
	                } else {
	                    System.out.println("One or both transfer accounts are null");
	                }
	                break;
	            default:
	                // Handle other transaction types here
	                break;
	        }

	        // Save the bank transaction
	        bankTransactionService.saveBankTransaction(bankTransaction);
	        return new ModelAndView("redirect:bankTransactionForm");
	        //return new ModelAndView("Hmm");
	    }
	}






	
	@RequestMapping("updateBankTransaction")
	public String forUpdatingTheBankTransaction(BankTransaction bankTransaction, Model model) {
	    BankTransaction retrievedBankTransaction = bankTransactionService.findById(bankTransaction.getBankTransactionId());
	    Account retrievedBankTransferFromAccount = accountService.findById(retrievedBankTransaction.getBankTransactionFromAccount().getAccountId());
	    Account retrievedBankTransferToAccount = accountService.findById(retrievedBankTransaction.getBankTransactionToAccount().getAccountId());
	    BankTransactionType retrievedbankTransactionType =  retrievedBankTransaction.getBankTransactionType();
	    
	    model.addAttribute("bankTransactions", bankTransactionService.findAll());
	    model.addAttribute("bt", retrievedBankTransaction);
	    model.addAttribute("bankTransactionFromAccount", retrievedBankTransferFromAccount);
	    model.addAttribute("bankTransactionToAccount", retrievedBankTransferToAccount);     
	    model.addAttribute("retrievedbankTransactionType", retrievedbankTransactionType);
	    model.addAttribute("bankTransactionTypes", BankTransactionType.values());

	    return "bankTransactionForm";
	}

	
	@RequestMapping("deleteBankTransaction")
	public String deletesTheBankTransaction(BankTransaction bankTransaction) {
		bankTransactionService.deleteById(bankTransaction.getBankTransactionId());
		return "redirect:bankTransactionForm";
	}
	
	
	  @RequestMapping("/filterTransactions") public ModelAndView filterTransactions(
			  @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
			  @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
	  
		  List<BankTransaction> filteredTransactions =
				  bankTransactionService.findByDateRange(startDate, endDate);
	  
		  ModelAndView mav = new ModelAndView("bankTransactionForm");
		  mav.addObject("bankTransactions", filteredTransactions);
		  mav.addObject("bankTransaction", new BankTransaction());
		  mav.addObject("bankTransactionTypes", BankTransactionType.values());
		  mav.addObject("nextBankTransactionId",
		  bankTransactionService.getNextBankTransactionId()); 
		  return mav; 
		  }
	  
	  @RequestMapping("findAllBankTransactions")
		public String findAll(BankTransaction bankTransaction, @RequestParam String sortBy, Model model) {
			model.addAttribute("bankTransactions", bankTransactionService.findAll(sortBy));
			return "bankTransactionForm";
		}
	 
}
	
	
