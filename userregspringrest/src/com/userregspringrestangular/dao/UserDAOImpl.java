package com.userregspringrestangular.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import org.springframework.transaction.annotation.Transactional;

import com.userregspringrestangular.model.User;


public class UserDAOImpl implements UserDAO {
	
	//private static final Logger logger = LoggerFactory.getLogger(UserDAOImpl.class);
	 
    private SessionFactory sessionFactory;
     
    public void setSessionFactory(SessionFactory sf){
        this.sessionFactory = sf;
    }
	
    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public void registerUser(User u) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(u);
        //logger.info("User created successfully, User Details=" + u);
    }
    
    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public List<User> listUsers() {
		
		Session session = this.sessionFactory.getCurrentSession();    	
    	Query query = session.createQuery("from User");
    	        
        List<User> usersList = query.list();
        for(User u : usersList){
            //logger.info("User:" + u.getEmailAddress());
        }
        return usersList;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public User getUserById(long id) {
    	Session session = this.sessionFactory.getCurrentSession();    	
    	User user = (User) session.get(User.class, id);
    	return user;
    }

}
