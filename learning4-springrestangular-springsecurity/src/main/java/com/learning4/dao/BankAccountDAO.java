package com.learning4.dao;

import java.util.List;

import com.learning4.model.BankAccount;
import com.learning4.model.TransactionHistory;

public interface BankAccountDAO extends GenericDAOInterface<BankAccount> {
		
	public List<BankAccount> getAllAccountsOfUser(long userId);
	
	public void transfer(TransactionHistory tH);
}
