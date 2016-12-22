package com.userregspringrestangular.controller;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.userregspringrestangular.model.Country;
import com.userregspringrestangular.dao.UserDAO;
import com.userregspringrestangular.model.User;

@RestController
@RequestMapping("/api")
public class SpringContactCRUDRestController implements ContactCRUDController {
	@Autowired
	private UserDAO userDAO;	
	
	@Override
	@RequestMapping(value =  "/user/create", method = RequestMethod.PUT)
	public void create(@RequestBody User u) {
		System.out.println("In create method");
		System.out.println("User Email: " + u.getEmailAddress());
		this.userDAO.registerUser(u);
	}
	
	@Override
	@RequestMapping(value =  "/user/{id}", method = RequestMethod.GET)
	public @ResponseBody User getById(@PathVariable("id") long id) {
		return this.userDAO.getUserById(id);		
	}
	
	@Override
	@RequestMapping(value =  "/users", method = RequestMethod.GET)
	public @ResponseBody List<User> listUsers() {
		return this.userDAO.listUsers();		
	}
	
	@Override
	@RequestMapping(value =  "/getallcountries", method = RequestMethod.GET)
	public List<Country> getCountries() {
		
		ArrayList<Country> countries = new ArrayList<Country>();
		
		System.out.println("In getCountries");
				
		Country country	 = new Country();
		country.setId(1);
		country.setName("Pakistan");
		countries.add(country);
		
		country	 = new Country();
		country.setId(2);
		country.setName("United States");
		countries.add(country);
				
		return countries;
	}

}
