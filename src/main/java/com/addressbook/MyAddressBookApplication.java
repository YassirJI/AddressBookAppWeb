package com.addressbook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.addressbook.utils.DataInitialiser;

@SpringBootApplication
public class MyAddressBookApplication implements CommandLineRunner {

	@Autowired
	private DataInitialiser dataInitialiser;
	
	public static void main(String[] args) {
		SpringApplication.run(MyAddressBookApplication.class, args);
	}

	public void run(String... args) throws Exception {
		dataInitialiser.init();
	}

}
