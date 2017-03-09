package com.userregistration.controller;

import java.util.List;

import com.userregistration.model.User;

public interface UserController {

	public String registerUser(User u);
	public List<User> listUsers();
}
