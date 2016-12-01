package com.addressbook.service;

import java.util.List;

import com.addressbook.model.Customer;

public interface CustomerService {

	public List<Customer> findAll();
	
	public List<Customer> findByName(String name);
	
	public void save(Customer customer);
	
	public void delete(Customer customer);
}
