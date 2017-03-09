package com.learning100.dao;

import java.util.List;

import com.learning100.model.Country;

public interface CountryDAO {
	 public void create(Country c);
	 public List<Country> getAll();
	 public Country getById(long id);

}
