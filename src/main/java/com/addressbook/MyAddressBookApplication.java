package com.addressbook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import com.addressbook.model.Customer;
import com.addressbook.model.User;
import com.addressbook.model.User.Role;
import com.addressbook.repository.CustomerRepository;
import com.addressbook.repository.UserRepository;
import com.addressbook.utils.PasswordEncoder;

@SpringBootApplication
public class MyAddressBookApplication implements CommandLineRunner {

	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(MyAddressBookApplication.class, args);
	}
	
	public void run(String... args) throws Exception {
		initUsers();
		initCustomers();
	}
	
	public void initCustomers(){
		customerRepository.deleteAll();

		customerRepository.save(new Customer("Alice", "0696784512","alice@yopmail.com"));
		customerRepository.save(new Customer("Bob", "0696784512","bob@yopmail.com"));
		customerRepository.save(new Customer("Yassir", "0696784512","yassir@yopmail.com"));
		customerRepository.save(new Customer("Mouad", "0696784512","mouad@yopmail.com"));
		customerRepository.save(new Customer("Adil", "0696784512","adil@yopmail.com"));
		customerRepository.save(new Customer("Boutain", "0696784512","boutaina@yopmail.com"));
		customerRepository.save(new Customer("Manal", "0696784512","manal@yopmail.com"));
		customerRepository.save(new Customer("Sara", "0696784512","Sara@yopmail.com"));
		customerRepository.save(new Customer("Aimane", "0696784512","aimane@yopmail.com"));
		customerRepository.save(new Customer("Rachid", "0696784512","rachid@yopmail.com"));
		customerRepository.save(new Customer("Sabir", "0696784512","sabir@yopmail.com"));
		customerRepository.save(new Customer("Jaafar", "0696784512","jaafar@yopmail.com"));
		customerRepository.save(new Customer("Nouha", "0696784512","nouha@yopmail.com"));
		customerRepository.save(new Customer("Sofia", "0696784512","nada@yopmail.com"));
		customerRepository.save(new Customer("Jihad", "0696784512","nada@yopmail.com"));
		customerRepository.save(new Customer("Nada", "0696784512","nada@yopmail.com"));
		customerRepository.save(new Customer("Khalid", "0696784512","nada@yopmail.com"));
		customerRepository.save(new Customer("Brahim", "0696784512","nada@yopmail.com"));
		customerRepository.save(new Customer("Jilali", "0696784512","nada@yopmail.com"));
		
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
