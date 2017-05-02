package com.learning5.service;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.learning5.dao.BankAccountDAO;
import com.learning5.dao.TransactionHistoryDAO;
import com.learning5.model.BankAccount;
import com.learning5.model.TransactionHistory;
import com.learning5.util.QueryConstants;

@Service
public class BankAccountManagerImpl implements BankAccountManager {
		
	@Autowired
	private BankAccountDAO bankAccountDAO;
	
	@Autowired
	private TransactionHistoryDAO transactionHistoryDAO;	
	
	private static final Logger logger = LoggerFactory.getLogger(BankAccountManagerImpl.class);
		
    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public void create(BankAccount bA) {
    	bankAccountDAO.save(bA);
        logger.info("BankAccount created successfully, User Details=" + bA);
    }
    
    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public List<BankAccount> getAll() {
    	List resultEntitiesList = null;
    	Map<String, Object> resultMap = bankAccountDAO.findAll();
    	if(resultMap!=null && resultMap.get(QueryConstants.RESULT_ENTITIES_LIST)!=null) {
    		resultEntitiesList = (List<BankAccount>)resultMap.get(QueryConstants.RESULT_ENTITIES_LIST);
    	}
        return resultEntitiesList;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public List<BankAccount> getAllAccountsOfUser(long userId) {		
        return bankAccountDAO.getAllAccountsOfUser(userId);
    }
    
    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public BankAccount getById(long id) {    	
    	return bankAccountDAO.findById(id);
    }
    
    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public void transfer(TransactionHistory tH) {
    	transactionHistoryDAO.save(tH);
        logger.info("Tranferred successfully, User Details=" + tH);
    }

}
