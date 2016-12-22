package com.userregspringrestangular.controller;

import java.util.List;

import org.springframework.web.bind.annotation.ResponseBody;

import com.userregspringrestangular.model.Contact;
import com.userregspringrestangular.model.Country;
import com.userregspringrestangular.model.User;

public interface ContactCRUDController {
	public void create(User userToCreate);	
	public User getById(long id);
	public List<User> listUsers();
	public List<Country> getCountries();
}
