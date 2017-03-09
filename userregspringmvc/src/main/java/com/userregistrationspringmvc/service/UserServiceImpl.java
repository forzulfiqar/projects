package com.userregistrationspringmvc.service;

import java.util.List;

//import javax.faces.bean.ManagedBean;
//import javax.faces.bean.SessionScoped;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
//import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.userregistrationspringmvc.dao.UserDAO;
import com.userregistrationspringmvc.model.User;

@Service
public class UserServiceImpl implements UserService {
	
private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Autowired	
	private UserDAO userDAO;	
			
	@Override
    @Transactional
    public String registerUser(User u) {		
		
        this.userDAO.registerUser(u);
        
        return "users.xhtml";
    }
	
	@Override
    @Transactional()
    public List<User> listUsers() {
		logger.info("In UserService.listUsers");
        return this.userDAO.listUsers();
    }
	

}
