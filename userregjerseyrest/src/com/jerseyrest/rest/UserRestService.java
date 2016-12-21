package com.jerseyrest.rest;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.jerseyrest.model.User;
import com.jerseyrest.model.Country;
import com.jerseyrest.dao.UserDAO;

@Component
@Path("/users")
public class UserRestService {
	
	@Autowired
	private UserDAO userDAO;	
		
	Map<Integer, User> contacts = new HashMap<Integer, User>();
	Map<Long, Country> countries = new HashMap<Long, Country>();
		
	@Path("/rest/getallusers")
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	@Transactional
	public List<User> getAllUsers() {
		
		System.out.println("In getAllUsers");
				
		List<User> users = this.userDAO.listUsers();
		
		System.out.println("Users list size: " + users.size());
				
		return users;
	}
	
	@Path("/rest/getuserbyid/{id}")
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	@Transactional
	public User getUserById(@PathParam("id") long id) {
		System.out.println("In getUserById");
		System.out.println("id: " + id);
		User s = null;
		if(id>0) {
			s = userDAO.getUserById(id);	
			System.out.println("DB user Email Address: " + s.getEmailAddress());
		}
		return s;
	}
	
	
	@PUT
	@Consumes({ MediaType.APPLICATION_JSON })
	//@Produces({ MediaType.APPLICATION_JSON })
	@Path("/rest/createuser")
	public void createNewUser(User c) throws Exception {
		
		System.out.println("In createNewUser");
		
		userDAO.registerUser(c);

	}	
	
	@Path("/rest/getallcountries")
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public List<Country> getCountries() {
		
		System.out.println("In getCountries");
				
		Country country	 = new Country();
		country.setId(1);
		country.setName("Pakistan");
		countries.put(country.getId(), country);
		
		country	 = new Country();
		country.setId(2);
		country.setName("United States");
		countries.put(country.getId(), country);
		
		System.out.println("countries size: " + countries.size());
				
		return new LinkedList<>(countries.values());
	}

}
