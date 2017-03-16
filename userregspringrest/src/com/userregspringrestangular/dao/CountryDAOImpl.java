package com.userregspringrestangular.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.userregspringrestangular.model.Country;
import com.userregspringrestangular.model.User;

@Repository
public class CountryDAOImpl extends GenericDAO<Country> implements CountryDAO  {
	
	private static final Logger logger = LoggerFactory.getLogger(CountryDAOImpl.class);
	
	public CountryDAOImpl() {
		super(Country.class);
	}
		
}
