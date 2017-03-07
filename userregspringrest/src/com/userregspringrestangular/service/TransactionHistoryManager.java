package com.userregspringrestangular.service;

import java.util.List;

import com.userregspringrestangular.model.TransactionHistory;

public interface TransactionHistoryManager {

	public void create(TransactionHistory tH);

	public List<TransactionHistory> getAll();

	public TransactionHistory getById(long id);

}
