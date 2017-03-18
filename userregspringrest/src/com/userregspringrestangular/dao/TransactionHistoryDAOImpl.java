package com.userregspringrestangular.dao;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.userregspringrestangular.model.TransactionHistory;

@Repository
public class TransactionHistoryDAOImpl extends GenericDAO<TransactionHistory> implements TransactionHistoryDAO  {
		
	private static final Logger logger = LoggerFactory.getLogger(TransactionHistoryDAOImpl.class);
	 
	public TransactionHistoryDAOImpl() {
		super(TransactionHistory.class);
	}
	
	public Map<String, Object> findTransactionsBetweenDates(long accountId, String fromtDate, String toDate, Map<String, Object> sortingAndPaginationParameters) {
		
		String queryString = "from TransactionHistory tH where tH.debitAccount.id = :debitAccountId or tH.creditAccount.id=:creditAccountId "
				+ "and tH.transactionDate between :fromDate and :toDate";
		
		Map<String, Object> queryParameters = new HashMap<String, Object>();	
		queryParameters.put("debitAccountId", accountId);
		queryParameters.put("creditAccountId", accountId);
		queryParameters.put("fromDate", fromtDate);
		queryParameters.put("toDate", toDate);
		
		return findResultForQuery(queryString, queryParameters, sortingAndPaginationParameters);
		
	}
}
