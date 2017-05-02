package com.learning5.dao;

import java.util.List;

import com.learning5.model.User;

public interface UserDAO extends GenericDAOInterface<User> {
	
	public User login(String userName, String password);

}
