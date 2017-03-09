package com.userregistrationspringmvc.service;

import java.util.List;

import com.userregistrationspringmvc.model.User;

public interface UserService {
	
	public String registerUser(User u);
	public List<User> listUsers();

}
