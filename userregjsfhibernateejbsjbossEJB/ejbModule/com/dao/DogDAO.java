package com.dao;

import javax.ejb.Stateless;

import com.model.Dog;

@Stateless
public class DogDAO extends GenericDAO<Dog> {

    public DogDAO() {
	super(Dog.class);
    }
    
    public void delete(Dog dog) {
        super.delete(dog.getId(), Dog.class);
    }
    
    //Extra methods in DAO
    public Dog testMessage() {
    	//Query query = getEm().createQuery("from Dog d where id=:id");
    	//return query.getSingleResult();    	
    	
    }
}