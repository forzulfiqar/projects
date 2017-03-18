package com.userregspringrestangular.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.userregspringrestangular.model.TransactionHistory;

public interface TransactionHistoryManager {

	public void create(TransactionHistory tH);

	public List<TransactionHistory> getAll();

	public TransactionHistory getById(long id);
	
	public Map<String, Object> findTransactionsBetweenDates(long accountId, String fromtDate, String toDate, Map<String, Object> sortingAndPaginationParameters);

}
