package com.learning3.dao;

import java.util.List;

import com.learning3.model.Country;
import com.learning3.model.Region;
import com.learning3.model.User;
import com.learning3.model.UsersReport;

public interface UserDAO {
	
	 public void registerUser(User u);
	 public List<User> listUsers();
	 public List<Country> listCoutries();
	 public List<Region> listRegions();
	 
	 public UsersReport prepareUsersReport();
	 public void preparePartialReport();
}
