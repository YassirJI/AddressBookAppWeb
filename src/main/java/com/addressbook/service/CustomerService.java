package com.addressbook.service;

import java.util.List;

import com.addressbook.model.Customer;

public interface CustomerService {

	List<Customer> findAll();

	List<Customer> findByNameOrEmailOrPhone(String value);

	void save(Customer customer);

	void delete(Customer customer);

}
