package com.learning100.controller;

import java.util.List;

import javax.ws.rs.core.Response;

import com.learning100.model.Country;
import com.learning100.model.User;

public interface UserController {
	public List<User> getAllUsers();

	public User getUserById(long id);

	public void createNewUser(User c) throws Exception;

	public List<Country> getCountries();
	
	public void login(User c) throws Exception;
	
	public void testTransactions(User c) throws Exception;	

}
