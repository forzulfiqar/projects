package com.userregistration.service;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.userregistration.dao.UserDAO;
import com.userregistration.model.User;

@Service
@ManagedBean(name="userService")
@SessionScoped
public class UserServiceImpl implements UserService {
	
private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	private UserDAO userDAO;	
			
	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}
		
	@Override
    @Transactional
    public String registerUser(User u) {		
		
        this.userDAO.registerUser(u);
        
        return "users.xhtml";
    }
	
	@Override
    @Transactional
    public List<User> listUsers() {
        return this.userDAO.listUsers();
    }
	

}
