package com.learning1.dao;

import java.util.List;

import com.learning1.model.Country;

public interface CountryDAO {
	 public void create(Country c);
	 public List<Country> getAll();
	 public Country getById(long id);

}
