package com.learning3.dao;

import java.util.Iterator;
import java.util.List;

import javax.swing.Scrollable;

import org.hibernate.Query;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.learning3.model.Country;
import com.learning3.model.Region;
import com.learning3.model.User;
import com.learning3.model.UsersReport;

@Repository
public class UserDAOImpl implements UserDAO {
	
	private static final Logger logger = LoggerFactory.getLogger(UserDAOImpl.class);
	 
    @Autowired
	private SessionFactory sessionFactory;
  	
    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public void registerUser(User u) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(u);
        logger.info("User created successfully, User Details=" + u);
    }
    
    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public List<User> listUsers() {
		
		Session session = this.sessionFactory.getCurrentSession();    	
    	Query query = session.createQuery("from User");
    	        
        List<User> usersList = query.list();
        for(User u : usersList){
            logger.info("User:" + u.getEmailAddress());
        }
        return usersList;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public List<Country> listCoutries() {
    	return null;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public List<Region> listRegions() {
		
		Session session = this.sessionFactory.getCurrentSession();    	
    	Query query = session.createQuery("select distinct reg FROM Region reg JOIN FETCH reg.countries cnt");
		//Query query = session.createQuery("select distinct reg FROM Region reg JOIN reg.countries cnt");
    	        
        List<Region> regionsList = query.list();
        for(Region r : regionsList){
            logger.info("Region:" + r.getName());
        }
        return regionsList;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public UsersReport prepareUsersReport() {
    	UsersReport usersReport = new UsersReport();
    	
    	usersReport.setMaxMarks(94);
    	usersReport.setNumberOfUsers(10);
    	return usersReport;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public void preparePartialReport() {
    	Session session = this.sessionFactory.getCurrentSession();    	
    	ScrollableResults  scrolla = session.createQuery("select reg.name, reg.id, count(reg.id) as cnt FROM Region reg group by reg.id").scroll();
    	while(scrolla.next()) {
    		System.out.println("name: " + scrolla.getString(0));
    		System.out.println("id: " + scrolla.getLong(1));
    		System.out.println("count: " + scrolla.getLong(2));
    	}
    	    	
    }

}
