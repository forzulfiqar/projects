package com.learning100.service;

import java.util.List;

import com.learning100.model.User;

public interface UserManager {
	 public void registerUser(User u);
	 public List<User> listUsers();
	 public User getUserById(long id);
	 public User login(User u);
	 public void testTransactions(User u) throws Exception;
}
