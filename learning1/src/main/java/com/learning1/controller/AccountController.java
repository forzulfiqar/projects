package com.learning1.controller;

import java.util.List;

import com.learning1.model.BankAccount;
import com.learning1.model.TransactionHistory;
import com.learning1.model.User;

public interface AccountController {

	public List<BankAccount> getAllAccounts();
	
	public List<BankAccount> getAllAccountsOfUser();

	public BankAccount getAccountById(long id);

	public void createNewAccount(BankAccount bA);
	
	public void transfer(TransactionHistory tH);

}
