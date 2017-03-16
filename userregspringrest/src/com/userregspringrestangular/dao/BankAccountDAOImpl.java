package com.userregspringrestangular.dao;

import java.util.List;

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

@Repository
public class BankAccountDAOImpl extends GenericDAO<BankAccount> implements BankAccountDAO  {

	private static final Logger logger = LoggerFactory.getLogger(BankAccountDAOImpl.class);

	public BankAccountDAOImpl() {
		super(BankAccount.class);
	 }
	
	@SuppressWarnings("unchecked")
	@Override
	public List<BankAccount> getAllAccountsOfUser(long userId) {

		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createQuery("from BankAccount bA where bA.user.id=:userId");
		query.setParameter("userId", userId);
		List<BankAccount> countriesList = query.list();
		return countriesList;
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
