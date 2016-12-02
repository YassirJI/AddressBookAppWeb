package com.addressbook.service;

import java.util.List;

import com.addressbook.model.User;

public interface UserService {

	public List<User> findAll();
	
	public void save(User user);
	
	public void delete(User user);
	
	User authenticate(String userName, String password);

}
