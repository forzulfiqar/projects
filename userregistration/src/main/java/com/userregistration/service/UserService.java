package com.userregistration.service;

import java.util.List;

import com.userregistration.model.User;

public interface UserService {
	
	public String registerUser(User u);
	public List<User> listUsers();

}
