package com.userregspringrestangular.controller;

import java.util.List;

import com.userregspringrestangular.model.BankAccount;
import com.userregspringrestangular.model.TransactionHistory;
import com.userregspringrestangular.model.User;

public interface AccountController {

	public List<BankAccount> getAllAccounts();
	
	public List<BankAccount> getAllAccountsOfUser();

	public BankAccount getAccountById(long id);

	public void createNewAccount(BankAccount bA);
	
	public void transfer(TransactionHistory tH);

}
