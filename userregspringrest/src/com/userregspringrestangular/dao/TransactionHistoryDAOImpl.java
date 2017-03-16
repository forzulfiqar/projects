package com.userregspringrestangular.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.userregspringrestangular.model.TransactionHistory;
import com.userregspringrestangular.model.User;

@Repository
public class TransactionHistoryDAOImpl extends GenericDAO<TransactionHistory> implements TransactionHistoryDAO  {
		
	private static final Logger logger = LoggerFactory.getLogger(TransactionHistoryDAOImpl.class);
	 
	public TransactionHistoryDAOImpl() {
		super(TransactionHistory.class);
	}
	
}
