package com.userregistrationspringmvc.dao;

import java.util.List;

import com.userregistrationspringmvc.model.Country;
import com.userregistrationspringmvc.model.Region;
import com.userregistrationspringmvc.model.User;
import com.userregistrationspringmvc.model.UsersReport;

public interface UserDAO {
	
	 public void registerUser(User u);
	 public List<User> listUsers();
	 public List<Country> listCoutries();
	 public List<Region> listRegions();
	 
	 public UsersReport prepareUsersReport();
	 public void preparePartialReport();
}
