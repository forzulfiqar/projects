package com.learning1.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.learning1.model.BankAccount;
import com.learning1.model.TransactionHistory;
import com.learning1.model.User;

@Repository
public class BankAccountDAOImpl implements BankAccountDAO {

	private static final Logger logger = LoggerFactory.getLogger(BankAccountDAOImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	@Override
	public void create(BankAccount c) {
		try {			
			Session session = this.sessionFactory.getCurrentSession();
			session.persist(c);
			logger.info("BankAccount created successfully, User Details=" + c);
		} catch (Exception e) {
			logger.error("Exception persisting user. Message= " + e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BankAccount> getAll() {

		Session session = this.sessionFactory.getCurrentSession();
		Query query = session.createQuery("from BankAccount");
		List<BankAccount> countriesList = query.list();
		return countriesList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<BankAccount> getAllAccountsOfUser(long userId) {

		Session session = this.sessionFactory.getCurrentSession();
		Query query = session.createQuery("from BankAccount bA where bA.user.id=:userId");
		query.setParameter("userId", userId);
		List<BankAccount> countriesList = query.list();
		return countriesList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public BankAccount getById(long id) {
		Session session = this.sessionFactory.getCurrentSession();
		BankAccount bankAccount = (BankAccount) session.get(BankAccount.class, id);
		return bankAccount;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void transfer(TransactionHistory tH) {
		try {			
			Session session = this.sessionFactory.getCurrentSession();			
			logger.info("BankAccountDAOImpl.transfer tH.getUser=" + tH.getUser());
			session.persist(tH);
			logger.info("Tranferred successfully, Details=" + tH);
		} catch (Exception e) {
			logger.error("Exception tranferring. Message= " + e);
		}
	}

}
