package com.addressbook.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;

import com.addressbook.model.User;

public interface UserRepository extends CrudRepository<User, String> {

	List<User> findAllBy(Pageable pageable);
	
	User findByUsernameAndPassword(String username, String password);
}
