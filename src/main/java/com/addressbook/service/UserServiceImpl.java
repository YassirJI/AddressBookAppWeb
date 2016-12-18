package com.addressbook.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.addressbook.model.Customer;
import com.addressbook.model.User;
import com.addressbook.repository.UserRepository;
import com.addressbook.utils.PasswordEncoder;
import com.google.common.collect.Sets;

@Service
public class UserServiceImpl implements UserService {

	private UserRepository userRepository;
	
	@Autowired
	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
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
		String generatedPassword = PasswordEncoder.generate(password);
		User user = userRepository.findByUsernameAndPassword(userName, generatedPassword);
        return user;
    }

	@Override
	public void addFavoriteCustomer(long id, Customer customer) {
		User user = userRepository.findOne(id);
		Set<Customer> customers = Sets.newHashSet(findCustomersByUser(id));
		customers.add(customer);
		user.setCustomers(customers);
		userRepository.save(user);
	}

	@Override
	public void removeFavoriteCustomer(long id, Customer customer) {
		User user = userRepository.findOne(id);
		Set<Customer> customers = Sets.newHashSet(findCustomersByUser(id));
		customers.remove(customer);
		user.setCustomers(customers);
		userRepository.save(user);
	}

	@Override
	public List<Customer> findCustomersByUser(long id) {
		return userRepository.findCustomersByUserId(id);
	}
}
