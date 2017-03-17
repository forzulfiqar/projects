package com.userregspringrestangular.dao;

import java.util.HashMap;
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

import com.userregspringrestangular.util.QueryConstants;

@Repository
public abstract class GenericDAO<T> implements GenericDAOInterface<T> {

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

	public void deleteById(long id) {
		Session session = this.sessionFactory.getCurrentSession();
		session.delete(findById(id));
	}

	public void deleteEntity(T entity) {
		Session session = this.sessionFactory.getCurrentSession();
		session.delete(entity);
	}

	public T update(T entity) {
		Session session = this.sessionFactory.getCurrentSession();
		return (T) session.merge(entity);
	}

	public T findById(long entityID) {
		Session session = this.sessionFactory.getCurrentSession();
		return (T) session.get(entityClass, entityID); // session.load loads the object with only id
														// populated and rest of the properties as proxy
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map<String, Object> findAll() {
		
		String queryString = "from " + entityClass.getName();
		
		logger.info("In GenericDAO findAll for: " + entityClass);
		
		return findResultForQuery(queryString, null, null);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> findResultForQuery(String queryString, Map<String, Object> queryParameters,
			Map<String, Object> sortingParameters) {
		
		String sortColumn = QueryConstants.DEFAULT_SORT_COLUMN;
		String sortOrder = QueryConstants.DEFAULT_SORT_ORDER;
		int currentPage = QueryConstants.DEFAULT_CURRENT_PAGE;
		int destinationPage = QueryConstants.DEFAULT_CURRENT_PAGE;
		int recordsPerPage = QueryConstants.DEFAULT_RECORDS_PER_PAGE;
				
		if(sortingParameters!=null && sortingParameters.size()>0) {
			if (sortingParameters.containsKey(QueryConstants.SORT_COLUMN)
					&& sortingParameters.get(QueryConstants.SORT_COLUMN) != null) {
				sortColumn = (String) sortingParameters.get(QueryConstants.SORT_COLUMN);
			}
			if (sortingParameters.containsKey(QueryConstants.SORT_ORDER)
					&& sortingParameters.get(QueryConstants.SORT_ORDER) != null) {
				sortOrder = (String) sortingParameters.get(QueryConstants.SORT_ORDER);
			}
			if (sortingParameters.containsKey(QueryConstants.CURRENT_PAGE)
					&& sortingParameters.get(QueryConstants.CURRENT_PAGE) != null) {
				currentPage = Integer.parseInt((String) sortingParameters.get(QueryConstants.CURRENT_PAGE));
			}
			if (sortingParameters.containsKey(QueryConstants.DESTINATION_PAGE)
					&& sortingParameters.get(QueryConstants.DESTINATION_PAGE) != null) {
				destinationPage = Integer.parseInt((String) sortingParameters.get(QueryConstants.DESTINATION_PAGE));
			}
			if (sortingParameters.containsKey(QueryConstants.RECORDS_PER_PAGE)
					&& sortingParameters.get(QueryConstants.RECORDS_PER_PAGE) != null) {
				recordsPerPage = Integer.parseInt((String) sortingParameters.get(QueryConstants.RECORDS_PER_PAGE));
			}
		}
		/*
		 * if(inputParameters.containsKey(QueryConstants.TOTAL_RECORDS) &&
		 * inputParameters.get(QueryConstants.TOTAL_RECORDS)!=null) { sortColumn
		 * = (String)inputParameters.get(QueryConstants.TOTAL_RECORDS); }
		 */

		if (queryString != null && queryString.length() > 0) {
			if (sortColumn != null && sortColumn.length() > 0) {
				queryString += " order by " + sortColumn;
				if (sortOrder != null && sortOrder.length() > 0) {
					queryString += " " + sortOrder;
				}
			}
		}

		if (sortingParameters != null && !sortingParameters.isEmpty()) {
			String key = null;
			Object value = null;
			for (Entry<String, Object> entry : sortingParameters.entrySet()) {
				key = entry.getKey();
				value = entry.getValue();
			}
		}

		Map<String, Object> result = new HashMap<String, Object>();

		List<T> listofEntities = null;
		int totalResults = 0;

		Session session = this.sessionFactory.getCurrentSession();

		try {
			Query query = session.createQuery(queryString);
			if (queryParameters != null && !queryParameters.isEmpty()) {
				populateQueryParameters(query, queryParameters);
			}

			listofEntities = query.list();
			totalResults = listofEntities.size();

			// @TODO: Limit the result to current page results based on
			// currentPage, destinationPage, recordsPerPage

			result.put(QueryConstants.RESULT_ENTITIES_LIST, listofEntities);
			result.put(QueryConstants.TOTAL_RECORDS, new Integer(totalResults));
			result.put(QueryConstants.CURRENT_PAGE, new Integer(destinationPage));
			result.put(QueryConstants.SORT_ORDER, sortOrder);
		} catch (Exception e) {
			System.out.println("Error while running query: " + e.getMessage());
			e.printStackTrace();
		}

		return result;
	}

	private void populateQueryParameters(Query query, Map<String, Object> parameters) {

		for (Entry<String, Object> entry : parameters.entrySet()) {
			query.setParameter(entry.getKey(), entry.getValue());
		}
	}

	public SessionFactory getSessionFactory() {
		return this.sessionFactory;
	}

}
