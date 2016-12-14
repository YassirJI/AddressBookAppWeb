package com.addressbook.service;

import java.util.List;

import com.addressbook.model.Customer;
import com.addressbook.model.User;

public interface UserService {

	List<User> findAll();

	void save(User user);

	void delete(User user);

	void addFavoriteCustomer(long id, Customer customer);
	
	void removeFavoriteCustomer(long id, Customer selectedCustomer);

	User authenticate(String userName, String password);

	List<Customer> findCustomersByUser(long id);


}
