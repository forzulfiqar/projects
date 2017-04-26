package com.learning3.service;

import java.util.List;

import com.learning3.model.User;

public interface UserService {
	
	public String registerUser(User u);
	public List<User> listUsers();

}
