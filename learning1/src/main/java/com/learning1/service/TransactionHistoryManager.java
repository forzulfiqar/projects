package com.learning1.service;

import java.util.List;

import com.learning1.model.TransactionHistory;

public interface TransactionHistoryManager {

	public void create(TransactionHistory tH);

	public List<TransactionHistory> getAll();

	public TransactionHistory getById(long id);

}
