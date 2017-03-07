package com.userregspringrestangular.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.userregspringrestangular.model.User;

@Repository
public class UserDAOImpl implements UserDAO {
	
	private static final Logger logger = LoggerFactory.getLogger(UserDAOImpl.class);
	 
	@Autowired
	private SessionFactory sessionFactory;
    	
    @SuppressWarnings("unchecked")
    @Override    
    public void registerUser(User u) {
    	try {
    		/*
    		if(true) {
    			throw new Exception("exception message");
    		}
    		*/
	        Session session = this.sessionFactory.getCurrentSession();
	        session.persist(u);
	        logger.info("User created successfully, User Details=" + u);
    	} catch(Exception e) {
    		logger.error("Exception persisting user. Message= " + e);
    	}
    }
    
    @SuppressWarnings("unchecked")
    @Override   
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
    public User getUserById(long id) {
    	Session session = this.sessionFactory.getCurrentSession();    	
    	User user = (User) session.get(User.class, id);
    	return user;
    }
    
    @SuppressWarnings("unchecked")
    @Override    
    public User login(User u) {
    	User user = null;
    	
    	Session session = this.sessionFactory.getCurrentSession();    	
    	Query query = session.createQuery("from User where userName=:userName and password=:password");
    	
    	query.setParameter("userName", u.getUserName());
    	query.setParameter("password", u.getPassword());
    	
        List<User> usersList = query.list();
        
    	if(usersList!=null && usersList.size()>0) {
    		user = usersList.get(0);
    		logger.info("Logged in user from DB: " + user.toString());
    	}
    	return user;
    }

}
