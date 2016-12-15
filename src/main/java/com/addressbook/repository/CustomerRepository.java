package com.addressbook.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.addressbook.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, String> {

	@Query("select c from Customer c where UPPER(c.name) like UPPER(CONCAT('%',:value, '%')) or UPPER(c.email) like UPPER(CONCAT('%',:value, '%')) or UPPER(c.phone) like UPPER(CONCAT('%',:value, '%'))")
	List<Customer> findByNameOrEmailOrPhoneContainingIgnoreCase(@Param("value") String value);
	
	List<Customer> findAllBy(Pageable pageable);

}
