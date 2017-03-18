package com.userregspringrestangular.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.userregspringrestangular.dao.TransactionHistoryDAO;
import com.userregspringrestangular.model.Country;
import com.userregspringrestangular.model.TransactionHistory;
import com.userregspringrestangular.util.QueryConstants;

@Service
public class TransactionHistoryManagerImpl implements TransactionHistoryManager {

	@Autowired
	private TransactionHistoryDAO transactionHistoryDAO;

	private static final Logger logger = LoggerFactory.getLogger(TransactionHistoryManagerImpl.class);

	public void setTransactionHistoryDAO(TransactionHistoryDAO transactionHistoryDAO) {
		this.transactionHistoryDAO = transactionHistoryDAO;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public void create(TransactionHistory tH) {
		transactionHistoryDAO.save(tH);
		logger.info("TransactionHistory created successfully, User Details=" + tH);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<TransactionHistory> getAll() {
		
		List resultEntitiesList = null;
    	Map<String, Object> resultMap = transactionHistoryDAO.findAll();
    	if(resultMap!=null && resultMap.get(QueryConstants.RESULT_ENTITIES_LIST)!=null) {
    		resultEntitiesList = (List<TransactionHistory>)resultMap.get(QueryConstants.RESULT_ENTITIES_LIST);
    	}
        return resultEntitiesList;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public TransactionHistory getById(long id) {
		return transactionHistoryDAO.findById(id);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional	
	public Map<String, Object> findTransactionsBetweenDates(long accountId, String fromtDate, String toDate, Map<String, Object> sortingAndPaginationParameters) {
		
		//List resultEntitiesList = null;
    	Map<String, Object> resultMap = transactionHistoryDAO.findTransactionsBetweenDates(accountId, fromtDate, toDate, sortingAndPaginationParameters);
    	/*
    	if(resultMap!=null && resultMap.get(QueryConstants.RESULT_ENTITIES_LIST)!=null) {
    		resultEntitiesList = (List<TransactionHistory>)resultMap.get(QueryConstants.RESULT_ENTITIES_LIST);
    	}
    	*/
        return resultMap;
	}	
}
