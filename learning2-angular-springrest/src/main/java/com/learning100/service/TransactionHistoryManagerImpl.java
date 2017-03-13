package com.learning100.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.learning100.dao.TransactionHistoryDAO;
import com.learning100.model.TransactionHistory;

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
	//@Transactional
	public void create(TransactionHistory c) {
		transactionHistoryDAO.create(c);
		logger.info("TransactionHistory created successfully, User Details=" + c);
	}

	@SuppressWarnings("unchecked")
	@Override
	//@Transactional
	public List<TransactionHistory> getAll() {
		return transactionHistoryDAO.getAll();
	}

	@SuppressWarnings("unchecked")
	@Override
	//@Transactional
	public TransactionHistory getById(long id) {
		return transactionHistoryDAO.getById(id);
	}

}
