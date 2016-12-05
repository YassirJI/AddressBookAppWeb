package com.addressbook.service;

import java.util.List;

import com.addressbook.model.User;

public interface UserService {

	List<User> findAll();

	void save(User user);

	void delete(User user);

	User authenticate(String userName, String password);

}
