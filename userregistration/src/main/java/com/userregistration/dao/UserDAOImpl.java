package com.userregistration.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.userregistration.model.User;

@Repository
public class UserDAOImpl extends GenericDAO<User> implements UserDAO  {
	
	private static final Logger logger = LoggerFactory.getLogger(UserDAOImpl.class);
	
	public UserDAOImpl() {
		super(User.class);
	 }
	
	/*
    @Autowired
	private SessionFactory sessionFactory;
   	
    @Override
    public void registerUser(User u) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(u);
        logger.info("User created successfully, User Details=" + u);
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
    */
}
