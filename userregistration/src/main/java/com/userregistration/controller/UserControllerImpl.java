package com.userregistration.controller;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import com.userregistration.dao.UserDAO;
import com.userregistration.model.User;
import com.userregistration.service.UserService;

@Component("userController")
//@ManagedBean(name="userController") //If this is written then it is not read as a spring bean. 
public class UserControllerImpl implements UserController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserControllerImpl.class);
	
	@Autowired
	private UserService userService;	
			
	@Override    
    public String registerUser(User u) {		
		
        this.userService.registerUser(u);
        
        return "users.xhtml";
    }
	
	@Override    
    public List<User> listUsers() {
		logger.info("In userController.listUsers");
        return this.userService.listUsers();
    }
	
}
