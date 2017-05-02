package com.learning5.dao;

import java.util.List;

import com.learning5.model.BankAccount;
import com.learning5.model.TransactionHistory;

public interface BankAccountDAO extends GenericDAOInterface<BankAccount> {
		
	public List<BankAccount> getAllAccountsOfUser(long userId);
	
	public void transfer(TransactionHistory tH);
}
