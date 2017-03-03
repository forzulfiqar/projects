package com.learning1.service;

import java.util.List;

import com.learning1.model.User;

public interface UserManager {
	 public void registerUser(User u);
	 public List<User> listUsers();
	 public User getUserById(long id);
	 public User login(User u);

}
