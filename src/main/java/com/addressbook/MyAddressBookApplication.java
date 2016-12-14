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
	
	@Transactional
	public void run(String... args) throws Exception {
		initUsers();
		initCustomers();
	}
	
	public void initCustomers(){
		customerRepository.deleteAll();

		customerRepository.save(new Customer("Alice", "Smith","alice@yopmail.com"));
		customerRepository.save(new Customer("Bob", "Smith","bob@yopmail.com"));
		customerRepository.save(new Customer("Yassir", "Smith","yassir@yopmail.com"));
		customerRepository.save(new Customer("Mouad", "Smith","mouad@yopmail.com"));
		customerRepository.save(new Customer("Adil", "Smith","adil@yopmail.com"));
		customerRepository.save(new Customer("Boutain", "Smith","boutaina@yopmail.com"));
		customerRepository.save(new Customer("Manal", "Smith","manal@yopmail.com"));
		customerRepository.save(new Customer("Sara", "Smith","Sara@yopmail.com"));
		customerRepository.save(new Customer("Aimane", "Smith","aimane@yopmail.com"));
		customerRepository.save(new Customer("Rachid", "Smith","rachid@yopmail.com"));
		customerRepository.save(new Customer("Sabir", "Smith","sabir@yopmail.com"));
		customerRepository.save(new Customer("Jaafar", "Smith","jaafar@yopmail.com"));
		customerRepository.save(new Customer("Nouha", "Smith","nouha@yopmail.com"));
		customerRepository.save(new Customer("Sofia", "Smith","nada@yopmail.com"));
		customerRepository.save(new Customer("Jihad", "Smith","nada@yopmail.com"));
		customerRepository.save(new Customer("Nada", "Smith","nada@yopmail.com"));
		customerRepository.save(new Customer("Khalid", "Smith","nada@yopmail.com"));
		customerRepository.save(new Customer("Brahim", "Smith","nada@yopmail.com"));
		customerRepository.save(new Customer("Jilali", "Smith","nada@yopmail.com"));
		
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
