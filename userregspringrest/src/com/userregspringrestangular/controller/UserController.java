package com.userregspringrestangular.controller;

import java.util.List;

import javax.ws.rs.core.Response;

import com.userregspringrestangular.model.Country;
import com.userregspringrestangular.model.User;

public interface UserController {
	public List<User> getAllUsers();

	public User getUserById(long id);

	public void createNewUser(User c) throws Exception;
	public void updateUser(User c) throws Exception;
	public void deleteUser(User c) throws Exception;

	public List<Country> getCountries();
	
	public void login(User c) throws Exception;

}
