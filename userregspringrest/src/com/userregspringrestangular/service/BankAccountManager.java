package com.userregspringrestangular.service;

import java.util.List;

import com.userregspringrestangular.model.BankAccount;
import com.userregspringrestangular.model.TransactionHistory;

public interface BankAccountManager {

	public void create(BankAccount bA);

	public List<BankAccount> getAll();
	
	public List<BankAccount> getAllAccountsOfUser(long userId);

	public BankAccount getById(long id);
	
	public void transfer(TransactionHistory tH);

}
