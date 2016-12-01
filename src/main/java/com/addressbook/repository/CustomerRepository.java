package com.addressbook.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.addressbook.model.Customer;

public interface CustomerRepository extends MongoRepository<Customer, String> {

	public List<Customer> findByNameIgnoreCase(String name);
	public Customer findByEmailIgnoreCase(String email);
	List<Customer> findAllBy(Pageable pageable);
    
}
