package com.addressbook.service;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.addressbook.model.AddressbookNotification;
import com.addressbook.model.User;
import com.addressbook.repository.UserRepository;

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
	};
	
	@Override	
	public void delete(User user) {
		userRepository.delete(user);
	}

	@Override
	public User authenticate(String userName, String password) {
		User user = new User();
        user.setFirstName("Yassir");
        user.setLastName("JANATI");
        user.setRole("admin");
        user.setEmail("admin@yopmail.com");
        user.setLocation("Loc");
        user.setBio("Quis aute iure reprehenderit in voluptate velit esse."
                + "Cras mattis iudicium purus sit amet fermentum.");
        return user;
    }

	@Override
	public int getUnreadNotificationsCount() {
		return 0;
	}

	@Override
	public Collection<AddressbookNotification> getNotifications() {
		return null;
	}
}
