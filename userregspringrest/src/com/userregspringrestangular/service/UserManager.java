package com.userregspringrestangular.service;

import java.util.List;

import com.userregspringrestangular.model.User;

public interface UserManager {
	 public void registerUser(User u);
	 public List<User> listUsers();
	 public User getUserById(long id);
	 public User login(User u);

}
