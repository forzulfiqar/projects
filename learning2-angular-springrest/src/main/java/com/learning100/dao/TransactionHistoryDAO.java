package com.learning100.dao;

import java.util.List;

import com.learning100.model.TransactionHistory;

public interface TransactionHistoryDAO {

	public void create(TransactionHistory c);

	public List<TransactionHistory> getAll();

	public TransactionHistory getById(long id);

}
