package com.userregspringrestangular.dao;

import java.util.List;

import com.userregspringrestangular.model.BankAccount;
import com.userregspringrestangular.model.TransactionHistory;

public interface BankAccountDAO extends GenericDAOInterface<BankAccount> {
		
	public List<BankAccount> getAllAccountsOfUser(long userId);
	
	public void transfer(TransactionHistory tH);
}
