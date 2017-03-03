package com.learning1.controller;

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

import com.learning1.model.Country;
import com.learning1.model.User;
import com.learning1.service.CountryManager;
import com.learning1.service.UserManager;
import com.learning1.service.UserManagerImpl;
import com.learning1.util.SessionData;

@Component
@Path("/users")
public class UserControllerImpl implements UserController {

	@Autowired
	private UserManager userManager;

	@Autowired
	private CountryManager countryManager;
	
	@Autowired
	private SessionData sessionData;
	
	private static final Logger logger = LoggerFactory.getLogger(UserControllerImpl.class);

	@Path("/rest/getallusers")
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	@Override
	public List<User> getAllUsers() {

		logger.info("In getAllUsers");

		List<User> users = this.userManager.listUsers();

		logger.info("Users list size: " + users.size());

		return users;
	}

	@Path("/rest/getuserbyid/{id}")
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	@Override
	public User getUserById(@PathParam("id") long id) {
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
	// @Produces({ MediaType.APPLICATION_JSON })
	@Path("/rest/createuser")
	@Override
	public void createNewUser(User c) throws Exception {

		logger.info("In createNewUser");

		userManager.registerUser(c);

	}

	@Path("/rest/getallcountries")
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	@Override
	public List<Country> getCountries() {

		logger.info("In getCountries");

		return countryManager.getAll();

	}
	
	@PUT
	@Consumes({ MediaType.APPLICATION_JSON })
	// @Produces({ MediaType.APPLICATION_JSON })
	@Path("/rest/login")
	@Override
	public Response login(User u) throws Exception {
		logger.info("In login");
		User user = userManager.login(u);
		String redirectURI = "../login.html";
		if(null!=user) {
			logger.info("Successfully logged In:" + user.toString());
			sessionData.setUser(user);
			redirectURI = "../loggedin.html";
		}
		
		java.net.URI location = new java.net.URI(redirectURI);
	    return Response.temporaryRedirect(location).build();

	}

}
