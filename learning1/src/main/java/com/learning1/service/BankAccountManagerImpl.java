package com.learning1.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.learning1.dao.BankAccountDAO;
import com.learning1.model.BankAccount;
import com.learning1.model.TransactionHistory;

@Service
public class BankAccountManagerImpl implements BankAccountManager {
		
	@Autowired
	private BankAccountDAO bankAccountDAO;	
	
	private static final Logger logger = LoggerFactory.getLogger(BankAccountManagerImpl.class);
	   
     
    public void setBankAccountDAO(BankAccountDAO bankAccountDAO){
        this.bankAccountDAO = bankAccountDAO;
    }
	
    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public void create(BankAccount c) {
    	bankAccountDAO.create(c);
        logger.info("BankAccount created successfully, User Details=" + c);
    }
    
    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public List<BankAccount> getAll() {		
        return bankAccountDAO.getAll();
    }
    
    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public List<BankAccount> getAllAccountsOfUser(long userId) {		
        return bankAccountDAO.getAll();
    }
    
    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public BankAccount getById(long id) {    	
    	return bankAccountDAO.getById(id);
    }
    
    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public void transfer(TransactionHistory tH) {
    	bankAccountDAO.transfer(tH);
        logger.info("Tranferred successfully, User Details=" + tH);
    }

}
