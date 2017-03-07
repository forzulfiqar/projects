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

@Repository
public class TransactionHistoryDAOImpl implements TransactionHistoryDAO {
		
	private static final Logger logger = LoggerFactory.getLogger(TransactionHistoryDAOImpl.class);
	 
	@Autowired
	private SessionFactory sessionFactory;
    	
    @SuppressWarnings("unchecked")
    @Override    
    public void create(TransactionHistory c) {
    	try {
    		if(true) {
    			throw new Exception("exception message");
    		}
	        Session session = this.sessionFactory.getCurrentSession();
	        session.persist(c);
	        logger.info("TransactionHistory created successfully, User Details=" + c);
    	} catch(Exception e) {
    		logger.error("Exception persisting user. Message= " + e);
    	}
    }
    
    @SuppressWarnings("unchecked")
    @Override   
    public List<TransactionHistory> getAll() {
		
		Session session = this.sessionFactory.getCurrentSession();    	
    	Query query = session.createQuery("from TransactionHistory");    	        
        List<TransactionHistory> countriesList = query.list();        
        return countriesList;
    }
    
    @SuppressWarnings("unchecked")
    @Override 
    public TransactionHistory getById(long id) {
    	Session session = this.sessionFactory.getCurrentSession();    	
    	TransactionHistory transactionHistory = (TransactionHistory) session.get(TransactionHistory.class, id);
    	return transactionHistory;
    }





}
