package com.userregspringrestangular.service;

import java.util.List;

import com.userregspringrestangular.model.Country;

public interface CountryManager {

	 public void create(Country c);
	 public List<Country> getAll();
	 public Country getById(long id);
}
