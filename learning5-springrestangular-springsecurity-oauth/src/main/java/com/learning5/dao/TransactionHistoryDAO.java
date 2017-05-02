package com.learning5.dao;

import java.util.Date;
import java.util.Map;

import com.learning5.model.TransactionHistory;

public interface TransactionHistoryDAO extends GenericDAOInterface<TransactionHistory> {
	public Map<String, Object> findTransactionsBetweenDates(long accountId, String fromtDate, String toDate, Map<String, Object> sortingAndPaginationParameters) ;			
}
