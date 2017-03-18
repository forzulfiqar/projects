package com.userregspringrestangular.dao;

import java.util.Date;
import java.util.Map;

import com.userregspringrestangular.model.TransactionHistory;

public interface TransactionHistoryDAO extends GenericDAOInterface<TransactionHistory> {
	public Map<String, Object> findTransactionsBetweenDates(long accountId, String fromtDate, String toDate, Map<String, Object> sortingAndPaginationParameters) ;			
}
