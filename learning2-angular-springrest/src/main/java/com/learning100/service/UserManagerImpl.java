package com.learning100.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.learning100.dao.UserDAO;
import com.learning100.model.User;

@Service
public class UserManagerImpl implements UserManager {
	
	@Autowired
	private UserDAO userDAO;	
	
	private static final Logger logger = LoggerFactory.getLogger(UserManagerImpl.class);
		
    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public void registerUser(User u) {
    	userDAO.registerUser(u);
        logger.info("User created successfully, User Details=" + u);
    }
    
    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public List<User> listUsers() {		
        return userDAO.listUsers();
    }
    
    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public User getUserById(long id) {    	
    	return userDAO.getUserById(id);
    }
    
    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public User login(User u) {
    	return userDAO.login(u);       
    }

}
