package com.userregspringrestangular.controller;

import java.util.List;

import com.userregspringrestangular.model.Contact;
import com.userregspringrestangular.model.User;

public interface ContactCRUDController {
	public void create(User userToCreate);
	public List<User> get();
	public User getById(Integer id);
}
