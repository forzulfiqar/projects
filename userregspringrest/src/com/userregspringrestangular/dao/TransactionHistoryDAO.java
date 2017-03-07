package com.userregspringrestangular.dao;

import java.util.List;

import com.userregspringrestangular.model.TransactionHistory;

public interface TransactionHistoryDAO {

	public void create(TransactionHistory c);

	public List<TransactionHistory> getAll();

	public TransactionHistory getById(long id);

}
