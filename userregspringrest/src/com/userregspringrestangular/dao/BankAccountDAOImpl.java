package com.userregspringrestangular.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.userregspringrestangular.model.BankAccount;
import com.userregspringrestangular.model.TransactionHistory;
import com.userregspringrestangular.model.User;
import com.userregspringrestangular.util.QueryConstants;

@Repository
public class BankAccountDAOImpl extends GenericDAO<BankAccount> implements BankAccountDAO  {

	private static final Logger logger = LoggerFactory.getLogger(BankAccountDAOImpl.class);

	public BankAccountDAOImpl() {
		super(BankAccount.class);
	 }
	
	@SuppressWarnings("unchecked")
	@Override
	public List<BankAccount> getAllAccountsOfUser(long userId) {

		List<BankAccount> bankAccountsList = null;		
		
		String queryString = "from BankAccount bA where bA.user.id=:userId";
		Map<String, Object> queryParametersMap = new HashMap<String, Object>();
		queryParametersMap.put("userId", userId);			
		
		Map<String, Object> result = findResultForQuery(queryString, queryParametersMap, null);
		bankAccountsList = (List<BankAccount>)result.get(QueryConstants.RESULT_ENTITIES_LIST);
		return bankAccountsList;		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void transfer(TransactionHistory tH) {
		try {			
			Session session = getSessionFactory().getCurrentSession();			
			logger.info("BankAccountDAOImpl.transfer tH.getUser=" + tH.getUser());
			session.persist(tH);
			logger.info("Tranferred successfully, Details=" + tH);
		} catch (Exception e) {
			logger.error("Exception tranferring. Message= " + e);
		}
	}

}
