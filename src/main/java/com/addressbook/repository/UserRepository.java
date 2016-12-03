package com.addressbook.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.addressbook.model.User;

public interface UserRepository extends MongoRepository<User, String> {

	List<User> findAllBy(Pageable pageable);
	
	User findByUsernameAndPassword(String username, String password);
}
