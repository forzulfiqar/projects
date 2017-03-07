package com.userregspringrestangular.controller;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.userregspringrestangular.model.Country;
import com.userregspringrestangular.model.User;
import com.userregspringrestangular.service.CountryManager;
import com.userregspringrestangular.service.UserManager;
import com.userregspringrestangular.service.UserManagerImpl;
import com.userregspringrestangular.util.SessionData;

@RestController
@Component
@RequestMapping("/users")
public class UserControllerImpl implements UserController {

	@Autowired
	private UserManager userManager;

	@Autowired
	private CountryManager countryManager;
	
	@Autowired
	private SessionData sessionData;
	
	private static final Logger logger = LoggerFactory.getLogger(UserControllerImpl.class);

	@RequestMapping("/rest/getallusers")
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	@Override
	public List<User> getAllUsers() {

		logger.info("In getAllUsers");

		List<User> users = this.userManager.listUsers();

		logger.info("Users list size: " + users.size());

		return users;
	}

	@RequestMapping("/rest/getuserbyid/{id}")
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	@Override
	public User getUserById(@PathVariable("id") long id) {
		logger.info("In getUserById");
		logger.info("id: " + id);
		User s = null;
		if (id > 0) {
			s = userManager.getUserById(id);
			logger.info("DB user Email Address: " + s.getEmailAddress());
		}
		return s;
	}

	@PUT
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	@RequestMapping("/rest/createuser")
	@Override
	public void createNewUser(@RequestBody User c) throws Exception {

		logger.info("In createNewUser");

		userManager.registerUser(c);

	}

	@RequestMapping("/rest/getallcountries")
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	@Override
	public List<Country> getCountries() {

		logger.info("In getCountries");

		return countryManager.getAll();

	}
	
	@PUT
	@Consumes({ MediaType.APPLICATION_JSON })
	//@Produces({ MediaType.APPLICATION_JSON })
	@RequestMapping("/rest/login")
	@Override
	public void login(@RequestBody User u) throws Exception {
		logger.info("In login");
		logger.info("User Name: " + u.getUserName());
		logger.info("Password: " + u.getPassword());
		User user = userManager.login(u);
		String redirectURI = "../login.html";
		if(null!=user) {
			logger.info("Successfully logged In:" + user.toString());
			sessionData.setUser(user);
			redirectURI = "../loggedin.html";
		}
		
		java.net.URI location = new java.net.URI(redirectURI);
	    //return Response.temporaryRedirect(location).build();

	}

}
