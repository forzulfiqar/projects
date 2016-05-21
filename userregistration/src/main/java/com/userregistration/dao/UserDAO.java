package com.userregistration.dao;

import java.util.List;

import com.userregistration.model.User;

public interface UserDAO {
	
	 public void registerUser(User u);
	 public List<User> listUsers();

}
