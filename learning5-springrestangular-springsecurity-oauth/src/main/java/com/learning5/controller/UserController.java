package com.learning5.controller;

import java.util.List;

import javax.ws.rs.core.Response;

import com.learning5.model.Country;
import com.learning5.model.User;

public interface UserController {
	public List<User> getAllUsers();

	public User getUserById(long id);

	public void createNewUser(User c) throws Exception;
	public void updateUser(User c) throws Exception;
	public void deleteUser(User c) throws Exception;

	public List<Country> getCountries();
	
	public void login(User c) throws Exception;

}
