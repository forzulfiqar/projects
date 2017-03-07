package com.userregspringrestangular.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.userregspringrestangular.model.Country;

@Repository
public class CountryDAOImpl implements CountryDAO {
	
	private static final Logger logger = LoggerFactory.getLogger(CountryDAOImpl.class);
	 
	@Autowired
	private SessionFactory sessionFactory;
    	
    @SuppressWarnings("unchecked")
    @Override    
    public void create(Country c) {
    	try {
    		Session session = this.sessionFactory.getCurrentSession();
	        session.persist(c);
	        logger.info("Country created successfully, User Details=" + c);
    	} catch(Exception e) {
    		logger.error("Exception persisting user. Message= " + e);
    	}
    }
    
    @SuppressWarnings("unchecked")
    @Override   
    public List<Country> getAll() {
		
		Session session = this.sessionFactory.getCurrentSession();    	
    	Query query = session.createQuery("from Country");    	        
        List<Country> countriesList = query.list();        
        return countriesList;
    }
    
    @SuppressWarnings("unchecked")
    @Override 
    public Country getById(long id) {
    	Session session = this.sessionFactory.getCurrentSession();    	
    	Country country = (Country) session.get(Country.class, id);
    	return country;
    }



}
