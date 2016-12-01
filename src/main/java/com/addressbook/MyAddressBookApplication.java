package com.addressbook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.addressbook.repository.CustomerRepository;

@SpringBootApplication
public class MyAddressBookApplication implements CommandLineRunner {

	@Autowired
	private CustomerRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(MyAddressBookApplication.class, args);
	}
	
	
	public void run(String... args) throws Exception {

//		repository.deleteAll();
//
//		// save a couple of customers
//		repository.save(new Customer("Alice", "Smith","alice@yopmail.com"));
//		repository.save(new Customer("Bob", "Smith","bob@yopmail.com"));
//		repository.save(new Customer("Yassir", "Smith","yassir@yopmail.com"));
//		repository.save(new Customer("Mouad", "Smith","mouad@yopmail.com"));
//		repository.save(new Customer("Adil", "Smith","adil@yopmail.com"));
//		repository.save(new Customer("Boutain", "Smith","boutaina@yopmail.com"));
//		repository.save(new Customer("Manal", "Smith","manal@yopmail.com"));
//		repository.save(new Customer("Sara", "Smith","Sara@yopmail.com"));
//		repository.save(new Customer("Aimane", "Smith","aimane@yopmail.com"));
//		repository.save(new Customer("Rachid", "Smith","rachid@yopmail.com"));
//		repository.save(new Customer("Sabir", "Smith","sabir@yopmail.com"));
//		repository.save(new Customer("Jaafar", "Smith","jaafar@yopmail.com"));
//		repository.save(new Customer("Nouha", "Smith","nouha@yopmail.com"));
//		repository.save(new Customer("Nada", "Smith","nada@yopmail.com"));
//		
//		// fetch all customers
//		System.out.println("Customers found with findAll():");
//		System.out.println("-------------------------------");
//		for (Customer customer : repository.findAll()) {
//			System.out.println(customer);
//		}
//		System.out.println();
//
//		// fetch an individual customer
//		System.out.println("Customer found with Email:");
//		System.out.println("--------------------------------");
//		System.out.println(repository.findByEmailIgnoreCase("yassir@yopmail.com"));
//
//		System.out.println("Customers found with findByLastName('Smith'):");
//		System.out.println("--------------------------------");
//		for (Customer customer : repository.findByNameIgnoreCase("Bob")) {
//			System.out.println(customer);
//		}

	}
}
