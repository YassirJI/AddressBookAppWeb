package com.addressbook.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.addressbook.model.Customer;
import com.addressbook.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	List<User> findAllBy(Pageable pageable);
	
	User findByUsernameAndPassword(String username, String password);

	@Query("SELECT u.customers FROM User u WHERE u.id = :id")
	public List<Customer> findCustomersByUserId(@Param("id") long id);
}
