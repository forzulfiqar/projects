package com.learning4.service;

import java.util.List;

import com.learning4.model.Country;

public interface CountryManager {

	 public void create(Country c);
	 public List<Country> getAll();
	 public Country getById(long id);
}
