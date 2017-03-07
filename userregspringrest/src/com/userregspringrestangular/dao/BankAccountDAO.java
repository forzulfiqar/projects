package com.userregspringrestangular.dao;

import java.util.List;

import com.userregspringrestangular.model.BankAccount;
import com.userregspringrestangular.model.TransactionHistory;

public interface BankAccountDAO {

	public void create(BankAccount c);

	public List<BankAccount> getAll();
	
	public List<BankAccount> getAllAccountsOfUser(long userId);

	public BankAccount getById(long id);
	
	public void transfer(TransactionHistory tH);
}
