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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


import com.userregistrationspringmvc.dao.UserDAO;
import com.userregistrationspringmvc.model.User;
import com.userregistrationspringmvc.service.UserService;
import com.userregistrationspringmvc.model.Country;
import com.userregistrationspringmvc.model.Region;

@Controller
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;	
	
    @RequestMapping(value = "/registeruser", method = RequestMethod.POST)
	public ModelAndView registerUser(@ModelAttribute("user") User user) {
    	System.out.println("In registerUser");
    	this.userService.registerUser(user);
    	System.out.println("Email Address: " + user.getEmailAddress());
		return listUsers();		
	}

	@RequestMapping(value = "/listusers", method = RequestMethod.GET)
	public ModelAndView listUsers() {

		System.out.println("In listUsers");
		
		this.userService.listUsers();
		
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
		

		List<User> listUsers = this.userService.listUsers();
		ModelAndView model = new ModelAndView("users");
		model.addObject("userList", listUsers);
		model.addObject("user", new User());
		model.addObject("countries", countries);
		return model;
	}
	
	@RequestMapping(value = { "/", "/home**" }, method = RequestMethod.GET)
	public ModelAndView welcomePage() {

		ModelAndView model = new ModelAndView();
		model.addObject("title", "Spring Security");
		model.addObject("message", "This is home page (After login)!");
		model.setViewName("home");
		return model;

	}

	@RequestMapping(value = "/admin**", method = RequestMethod.GET)
	public ModelAndView adminPage() {

		ModelAndView model = new ModelAndView();
		model.addObject("title", "Spring Security");
		model.addObject("message", "This is admin page (Logged in user only)!");
		model.setViewName("admin");

		return model;

	}
	
	//Spring Security see this :
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login(
		@RequestParam(value = "error", required = false) String error,
		@RequestParam(value = "logout", required = false) String logout) {

		ModelAndView model = new ModelAndView();
		if (error != null) {
			model.addObject("error", "Invalid username and password!");
		}

		if (logout != null) {
			model.addObject("msg", "You've been logged out successfully.");
		}
		model.setViewName("login");

		return model;

	}
	
}
