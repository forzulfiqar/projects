package com.learning5.service;

import java.util.List;

import com.learning5.model.Country;

public interface CountryManager {

	 public void create(Country c);
	 public List<Country> getAll();
	 public Country getById(long id);
}
