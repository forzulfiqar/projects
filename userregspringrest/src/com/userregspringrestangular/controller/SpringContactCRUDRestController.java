package com.userregspringrestangular.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.userregspringrestangular.dao.UserDAO;
import com.userregspringrestangular.model.Contact;
import com.userregspringrestangular.model.User;

@RestController
public class SpringContactCRUDRestController implements ContactCRUDController {
	@Autowired
	private UserDAO userDAO;	
	
	@Override
	@RequestMapping(path = "/api/user/create", method = RequestMethod.PUT)
	public void create(@RequestBody User contactToCreate) {
		//this.userDAO.registerUser(contactToCreate);
	}

	@Override
	@RequestMapping(path = "/api/users", method = RequestMethod.GET)
	public @ResponseBody List<User> get() {
		return this.userDAO.listUsers();		
	}

	@Override
	@RequestMapping(path = "/api/user/{id}", method = RequestMethod.GET)
	public @ResponseBody User getById(@PathVariable("id") Integer id) {
		//return this.userDAO.getById(id);
		return null;
	}

}
