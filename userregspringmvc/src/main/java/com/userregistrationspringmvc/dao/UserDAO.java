package com.userregistrationspringmvc.dao;

import java.util.List;

import com.userregistrationspringmvc.model.Country;
import com.userregistrationspringmvc.model.User;

public interface UserDAO {
	
	 public void registerUser(User u);
	 public List<User> listUsers();
	 public List<Country> listCoutries();

}
