package com.learning4.service;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.learning4.dao.CountryDAO;
import com.learning4.model.BankAccount;
import com.learning4.model.Country;
import com.learning4.util.QueryConstants;

@Service
public class CountryManagerImpl implements CountryManager {
	
	@Autowired
	private CountryDAO countryDAO;	
	
	private static final Logger logger = LoggerFactory.getLogger(CountryManagerImpl.class);
	   
     
    public void setCountryDAO(CountryDAO countryDAO){
        this.countryDAO = countryDAO;
    }
	
    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public void create(Country c) {
    	countryDAO.save(c);
        logger.info("Country created successfully, User Details=" + c);
    }
    
    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public List<Country> getAll() {	
    	
    	List resultEntitiesList = null;
    	Map<String, Object> resultMap = countryDAO.findAll();
    	if(resultMap!=null && resultMap.get(QueryConstants.RESULT_ENTITIES_LIST)!=null) {
    		resultEntitiesList = (List<Country>)resultMap.get(QueryConstants.RESULT_ENTITIES_LIST);
    	}
        return resultEntitiesList;        
    }
    
    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public Country getById(long id) {    	
    	return countryDAO.findById(id);
    }
    
}
