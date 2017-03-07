package com.userregspringrestangular.dao;

import java.util.List;

import com.userregspringrestangular.model.Country;

public interface CountryDAO {
	 public void create(Country c);
	 public List<Country> getAll();
	 public Country getById(long id);

}
