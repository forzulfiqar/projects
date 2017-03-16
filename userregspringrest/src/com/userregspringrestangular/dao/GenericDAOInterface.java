package com.userregspringrestangular.dao;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public interface GenericDAOInterface<T> {

	public void save(T entity);

	public void delete(long id);

	public T update(T entity); 

	public T find(long entityID);

	public List<T> findAll();

	public T findOneResult(String namedQuery, Map<String, Object> parameters);

	public SessionFactory getSessionFactory();

}
