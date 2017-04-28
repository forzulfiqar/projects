package com.learning4.service;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.learning4.dao.UserDAO;
import com.learning4.model.TransactionHistory;
import com.learning4.model.User;
import com.learning4.util.QueryConstants;

@Service
public class UserManagerImpl implements UserManager {
	
	@Autowired
	private UserDAO userDAO;	
	
	private static final Logger logger = LoggerFactory.getLogger(UserManagerImpl.class);
		
    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public void registerUser(User u) {
    	userDAO.save(u);
        logger.info("User created successfully, User Details=" + u);
    }
    
    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public void updateUser(User u) {
    	userDAO.update(u);
        logger.info("User created successfully, User Details=" + u);
    }
    
    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public void deleteUser(User u) {
    	userDAO.deleteById(u.getId());
        logger.info("User created successfully, User Details=" + u);
    }
    
    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public List<User> listUsers() {		
    	
    	List resultEntitiesList = null;
    	Map<String, Object> resultMap = userDAO.findAll();
    	if(resultMap!=null && resultMap.get(QueryConstants.RESULT_ENTITIES_LIST)!=null) {
    		resultEntitiesList = (List<User>)resultMap.get(QueryConstants.RESULT_ENTITIES_LIST);
    	}
        return resultEntitiesList;
	}
    
    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public User getUserById(long id) {    	
    	return userDAO.findById(id);
    }
    
    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public User login(String userName, String password) {
    	return userDAO.login(userName, password);       
    }

}
