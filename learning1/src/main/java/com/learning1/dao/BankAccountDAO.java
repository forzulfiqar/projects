package com.learning1.dao;

import java.util.List;

import com.learning1.model.BankAccount;
import com.learning1.model.TransactionHistory;

public interface BankAccountDAO {

	public void create(BankAccount c);

	public List<BankAccount> getAll();
	
	public List<BankAccount> getAllAccountsOfUser(long userId);

	public BankAccount getById(long id);
	
	public void transfer(TransactionHistory tH);
}
