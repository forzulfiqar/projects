package com.learning4.service;

import java.util.List;

import com.learning4.model.User;

public interface UserManager {
	 public void registerUser(User u);
	 public void updateUser(User u);
	 public void deleteUser(User u);
	 public List<User> listUsers();
	 public User getUserById(long id);
	 public User login(String userName, String password);

}
