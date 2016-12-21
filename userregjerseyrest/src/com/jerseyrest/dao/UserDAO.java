package com.jerseyrest.dao;

import java.util.List;

import com.jerseyrest.model.User;

public interface UserDAO {
	
	 public void registerUser(User u);
	 public List<User> listUsers();
	 public User getUserById(long id);

}
