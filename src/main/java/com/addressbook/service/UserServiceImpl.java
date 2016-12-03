package com.addressbook.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.addressbook.model.User;
import com.addressbook.repository.UserRepository;
import com.addressbook.utils.PasswordHashGenerator;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public List<User> findAll(){
		return userRepository.findAll();
	}
	
	@Override	
	public void save(User user) {
		userRepository.save(user);	
	}
	
	@Override	
	public void delete(User user) {
		userRepository.delete(user);
	}

	@Override
	public User authenticate(String userName, String password) {
		String generatedPassword = PasswordHashGenerator.generate(password);
		User user = userRepository.findByUsernameAndPassword(userName, generatedPassword);
        return user;
    }
}
