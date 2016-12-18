package com.addressbook.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.addressbook.model.Customer;
import com.addressbook.model.User;
import com.addressbook.model.User.Role;
import com.addressbook.repository.CustomerRepository;
import com.addressbook.repository.UserRepository;

@Component
public class DataInitialiser {

	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private UserRepository userRepository;

	public void init() {
		initUsers();
		initCustomers();
	}

	public void initCustomers(){
		customerRepository.deleteAll();

		customerRepository.save(new Customer("Alice BERCLA", "0696784512","alice@yopmail.com"));
		customerRepository.save(new Customer("Bob DAVID", "0696784512","bob@yopmail.com"));
		customerRepository.save(new Customer("Yassir JANATI", "0696784512","yassir@yopmail.com"));
		customerRepository.save(new Customer("Mouad IDRISSI", "0696784512","mouad@yopmail.com"));
		customerRepository.save(new Customer("Adil BBO", "0696784512","adil@yopmail.com"));
		customerRepository.save(new Customer("Boutaina KAL", "0696784512","boutaina@yopmail.com"));
		customerRepository.save(new Customer("Manal AZO", "0696784512","manal@yopmail.com"));
		customerRepository.save(new Customer("Sara OZAM", "0696784512","Sara@yopmail.com"));
		customerRepository.save(new Customer("Aimane IAZX", "0696784512","aimane@yopmail.com"));
		customerRepository.save(new Customer("Rachid YAZDZ", "0696784512","rachid@yopmail.com"));
		customerRepository.save(new Customer("Sabir OPERT", "0696784512","sabir@yopmail.com"));
		customerRepository.save(new Customer("Jaafar BANZU", "0696784512","jaafar@yopmail.com"));
		customerRepository.save(new Customer("Nouha OAZEE", "0696784512","nouha@yopmail.com"));
		customerRepository.save(new Customer("Sofia KALEMPO", "0696784512","sofia@yopmail.com"));
		customerRepository.save(new Customer("Jihad NORTE", "0696784512","jihad@yopmail.com"));
		customerRepository.save(new Customer("Nada YASIK", "0696784512","nada@yopmail.com"));
		customerRepository.save(new Customer("Khalid OPEJS", "0696784512","khalid@yopmail.com"));
		customerRepository.save(new Customer("Brahim AMEKRA", "0696784512","brahim@yopmail.com"));
		customerRepository.save(new Customer("Jilali LARBAI", "0696784512","jilali@yopmail.com"));
		
	}
	
	public void initUsers(){
		userRepository.deleteAll();
		
		User user = new User("Mr", "Yassir","JANATI", Role.USER,"yassirji@yopmail.com", "0621781278", "FRANCE");
		user.setUsername("yassirji");
		user.setPassword(PasswordEncoder.generate("yassirji"));
        userRepository.save(user);
        
        user = new User("Mr", "Admin","Admin",Role.ADMIN,"admin@yopmail.com", "0678218721", "ENGLAND");
		user.setUsername("admin");
		user.setPassword(PasswordEncoder.generate("admin"));
        userRepository.save(user);
		
	}


}
