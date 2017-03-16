package com.userregistration.dao;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public abstract class GenericDAO<T> implements GenericDAOInterface<T>{
	
	private static final Logger logger = LoggerFactory.getLogger(GenericDAO.class);
	
	@Autowired
	private SessionFactory sessionFactory;
   	
    private Class<T> entityClass;

    public GenericDAO(Class<T> entityClass) {
	this.entityClass = entityClass;
    }

    public void save(T entity) {
    	logger.info("In Generic DAO save for: " + entityClass);
    	Session session = this.sessionFactory.getCurrentSession();
    	logger.info("1111111: " + entityClass);
    	session.persist(entity);
    	logger.info("222222" + entityClass);
    }

    public void delete(long id, Class<T> classe) {
    	Session session = this.sessionFactory.getCurrentSession();    	
    	session.delete(find(id));
    }

    public T update(T entity) {
    	Session session = this.sessionFactory.getCurrentSession();
    	return (T)session.merge(entity);
    }

    public T find(long entityID) {
    	Session session = this.sessionFactory.getCurrentSession();
    	return (T) session.load(entityClass, entityID);	
    }

    // Using the unchecked because JPA does not have a
    // em.getCriteriaBuilder().createQuery()<T> method
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public List<T> findAll() {
    	logger.info("In GenericDAO findAll for: " + entityClass);
    	Session session = this.sessionFactory.getCurrentSession();
    	logger.info("1111111: " + entityClass);
    	Criteria criteria = session.createCriteria(entityClass);
    	logger.info("222222: " + entityClass);
    	return criteria.list();	
    }

    @SuppressWarnings("unchecked")
    public T findOneResult(String namedQuery, Map<String, Object> parameters) {
	T result = null;
	
	Session session = this.sessionFactory.getCurrentSession();

	try {
		Query query = session.createQuery("");
	    //Query query = em.createNamedQuery(namedQuery);

	    // Method that will populate parameters if they are passed not null and empty
	    if (parameters != null && !parameters.isEmpty()) {
		populateQueryParameters(query, parameters);
	    }

	    result = (T) query.list().get(0);

	} catch (Exception e) {
	    System.out.println("Error while running query: " + e.getMessage());
	    e.printStackTrace();
	}

	return result;
    }

    public void populateQueryParameters(Query query, Map<String, Object> parameters) {

	for (Entry<String, Object> entry : parameters.entrySet()) {
	    query.setParameter(entry.getKey(), entry.getValue());
	}
    }

	public SessionFactory getSessionFactory() {
		return this.sessionFactory;
	}
    
}
