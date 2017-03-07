package com.userregspringrestangular.dao;

import java.util.List;

import com.userregspringrestangular.model.User;

public interface UserDAO {
	
	 public void registerUser(User u);
	 public List<User> listUsers();
	 public User getUserById(long id);
	 
	 public User login(User u);

}
