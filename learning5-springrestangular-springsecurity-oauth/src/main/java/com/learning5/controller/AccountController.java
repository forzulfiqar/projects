package com.learning5.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.PathVariable;

import com.learning5.model.BankAccount;
import com.learning5.model.TransactionHistory;
import com.learning5.model.User;

public interface AccountController {

	public List<BankAccount> getAllAccounts();
	
	public List<BankAccount> getAllAccountsOfUser();

	public BankAccount getAccountById(long id);

	public void createNewAccount(BankAccount bA);
	
	public void transfer(TransactionHistory tH);
	
	public TransactionHistory getTransactionById(long id);
	
	public List<TransactionHistory> findTransactionsBetweenDates(Long accountId, String fromtDate, String toDate);
}
