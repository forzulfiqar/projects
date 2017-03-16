package com.userregspringrestangular.dao;

import java.util.List;

import com.userregspringrestangular.model.User;

public interface UserDAO extends GenericDAOInterface<User> {
	
	public User login(String userName, String password);

}
