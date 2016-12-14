package com.addressbook.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.addressbook.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, String> {

	List<Customer> findByNameIgnoreCase(String name);
	
	Customer findByEmailIgnoreCase(String email);

	List<Customer> findAllBy(Pageable pageable);

}
