package com.userregistrationspringmvc.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


import com.userregistrationspringmvc.dao.UserDAO;
import com.userregistrationspringmvc.model.User;
import com.userregistrationspringmvc.model.Country;

@Controller
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserDAO userDAO;	
	
    @RequestMapping(value = "/registeruser", method = RequestMethod.POST)
	public ModelAndView registerUser(@ModelAttribute("user") User user) {
    	System.out.println("In registerUser");
    	this.userDAO.registerUser(user);
    	System.out.println("Email Address: " + user.getEmailAddress());
		return listUsers();		
	}

	@RequestMapping(value = "/listusers", method = RequestMethod.GET)
	public ModelAndView listUsers() {

		System.out.println("In listUsers");
		
		userDAO.listUsers();
		
		List<Country> countries = new ArrayList<Country>();
		
		Country country	 = new Country();
		country.setId(1);
		country.setName("United States");
		countries.add(country);
		
		country	 = new Country();
		country.setId(2);
		country.setName("United Kingdom");
		countries.add(country);		

		
		country	 = new Country();
		country.setId(3);
		country.setName("Pakistan");
		countries.add(country);		
		

		List<User> listUsers = this.userDAO.listUsers();
		ModelAndView model = new ModelAndView("users");
		model.addObject("userList", listUsers);
		model.addObject("user", new User());
		model.addObject("countries", countries);
		return model;
	}

}
