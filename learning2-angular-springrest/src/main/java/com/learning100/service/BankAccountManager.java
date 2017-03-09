package com.learning100.service;

import java.util.List;

import com.learning100.model.BankAccount;
import com.learning100.model.TransactionHistory;

public interface BankAccountManager {

	public void create(BankAccount bA);

	public List<BankAccount> getAll();
	
	public List<BankAccount> getAllAccountsOfUser(long userId);

	public BankAccount getById(long id);
	
	public void transfer(TransactionHistory tH);

}
