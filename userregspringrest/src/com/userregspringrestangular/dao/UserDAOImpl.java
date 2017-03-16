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
public class UserDAOImpl extends GenericDAO<User> implements UserDAO  {
	
	private static final Logger logger = LoggerFactory.getLogger(UserDAOImpl.class);
	
	public UserDAOImpl() {
		super(User.class);
	 }
		
    @SuppressWarnings("unchecked")
    @Override
    public User login(String userName, String password) {
    	User user = null;
    	
		Session session = getSessionFactory().getCurrentSession(); 		
    	Query query = session.createQuery("from User u where u.userName=:userName and u.password=:password");
    	  
    	query.setParameter("userName", userName);
    	query.setParameter("password", password);
    	    	
        List<User> usersList = query.list();
        if(usersList!=null && usersList.size()>0) {
        	user = usersList.get(0);
        }
        
        return user;
    }
    
}
