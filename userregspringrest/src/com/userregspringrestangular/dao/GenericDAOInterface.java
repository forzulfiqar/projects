package com.userregspringrestangular.dao;

import java.util.Map;
import java.util.Map.Entry;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public interface GenericDAOInterface<T> {
	public void save(T entity);

	public T update(T entity);

	public void deleteById(long id);

	public void deleteEntity(T entity);

	public T findById(long entityID);

	public Map<String, Object> findAll();

	public SessionFactory getSessionFactory();
}
