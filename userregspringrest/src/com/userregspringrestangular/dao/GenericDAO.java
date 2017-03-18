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
		Session session = this.sessionFactory.getCurrentSession();		
		session.persist(entity);		
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
		return (T) session.get(entityClass, entityID); //Loads object with all values populated
	}
	
	public T loadById(long entityID) {
		Session session = this.sessionFactory.getCurrentSession();
		return (T) session.load(entityClass, entityID); //Loads object with only id populated and all other values are proxies
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map<String, Object> findAll() {
		
		String queryString = "from " + entityClass.getName();
		
		logger.info("In GenericDAO findAll for: " + entityClass);
		
		return findResultForQuery(queryString, null, null);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> findResultForQuery(String queryString, Map<String, Object> queryParameters,
			Map<String, Object> sortingAndPaginationParameters) {
		
		logger.info("In GenericDAO.findResultForQuery: " + queryString);
		
		String sortColumn = QueryConstants.DEFAULT_SORT_COLUMN;
		String sortOrder = QueryConstants.DEFAULT_SORT_ORDER;
		int currentPage = QueryConstants.DEFAULT_CURRENT_PAGE;
		int destinationPage = QueryConstants.DEFAULT_CURRENT_PAGE;
		int recordsPerPage = QueryConstants.DEFAULT_RECORDS_PER_PAGE;
				
		if(sortingAndPaginationParameters!=null && sortingAndPaginationParameters.size()>0) {
			if (sortingAndPaginationParameters.containsKey(QueryConstants.SORT_COLUMN)
					&& sortingAndPaginationParameters.get(QueryConstants.SORT_COLUMN) != null) {
				sortColumn = (String) sortingAndPaginationParameters.get(QueryConstants.SORT_COLUMN);
			}
			if (sortingAndPaginationParameters.containsKey(QueryConstants.SORT_ORDER)
					&& sortingAndPaginationParameters.get(QueryConstants.SORT_ORDER) != null) {
				sortOrder = (String) sortingAndPaginationParameters.get(QueryConstants.SORT_ORDER);
			}
			if (sortingAndPaginationParameters.containsKey(QueryConstants.CURRENT_PAGE)
					&& sortingAndPaginationParameters.get(QueryConstants.CURRENT_PAGE) != null) {
				currentPage = Integer.parseInt((String) sortingAndPaginationParameters.get(QueryConstants.CURRENT_PAGE));
			}
			if (sortingAndPaginationParameters.containsKey(QueryConstants.DESTINATION_PAGE)
					&& sortingAndPaginationParameters.get(QueryConstants.DESTINATION_PAGE) != null) {
				destinationPage = Integer.parseInt((String) sortingAndPaginationParameters.get(QueryConstants.DESTINATION_PAGE));
			}
			if (sortingAndPaginationParameters.containsKey(QueryConstants.RECORDS_PER_PAGE)
					&& sortingAndPaginationParameters.get(QueryConstants.RECORDS_PER_PAGE) != null) {
				recordsPerPage = Integer.parseInt((String) sortingAndPaginationParameters.get(QueryConstants.RECORDS_PER_PAGE));
			}
		}
		
		if (queryString != null && queryString.length() > 0) {
			if (sortColumn != null && sortColumn.length() > 0) {
				queryString += " order by " + sortColumn;
				if (sortOrder != null && sortOrder.length() > 0) {
					queryString += " " + sortOrder;
				}
			}
		}

		if (sortingAndPaginationParameters != null && !sortingAndPaginationParameters.isEmpty()) {
			String key = null;
			Object value = null;
			for (Entry<String, Object> entry : sortingAndPaginationParameters.entrySet()) {
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

			totalResults = query.list().size();
			//totalResults = listofEntities.size();

			// @TODO: Every query will run for two times
			
			int startRecordsFrom = (destinationPage-1) * recordsPerPage;
			query.setFirstResult(startRecordsFrom);
			query.setMaxResults(recordsPerPage);
			listofEntities = query.list();

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
