package com.learning4.dao;

import java.util.List;

import com.learning4.model.User;

public interface UserDAO extends GenericDAOInterface<User> {
	
	public User login(String userName, String password);

}
