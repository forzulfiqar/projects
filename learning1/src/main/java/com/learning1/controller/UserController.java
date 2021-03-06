package com.learning1.controller;

import java.util.List;

import javax.ws.rs.core.Response;

import com.learning1.model.Country;
import com.learning1.model.User;

public interface UserController {
	public List<User> getAllUsers();

	public User getUserById(long id);

	public void createNewUser(User c) throws Exception;

	public List<Country> getCountries();
	
	public Response login(User c) throws Exception;

}
