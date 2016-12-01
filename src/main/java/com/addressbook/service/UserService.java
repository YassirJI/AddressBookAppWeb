package com.addressbook.service;

import java.util.Collection;
import java.util.List;

import com.addressbook.model.AddressbookNotification;
import com.addressbook.model.User;

public interface UserService {

	public List<User> findAll();
	
	public void save(User user);
	
	public void delete(User user);
	
	User authenticate(String userName, String password);

    int getUnreadNotificationsCount();

	Collection<AddressbookNotification> getNotifications();

}
