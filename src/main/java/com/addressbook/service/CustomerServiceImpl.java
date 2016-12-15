package com.addressbook.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.addressbook.model.Customer;
import com.addressbook.repository.CustomerRepository;

@Service
public class CustomerServiceImpl implements CustomerService {

	private CustomerRepository customerRepository;
	
	@Autowired
	private CustomerServiceImpl(CustomerRepository customerRepository){
		this.customerRepository = customerRepository;
	}
	
	@Override
	public List<Customer> findAll(){
		return customerRepository.findAll();
	}
	
	@Override	
	public List<Customer> findByNameOrEmailOrPhone(String value){
		return customerRepository.findByNameOrEmailOrPhoneContainingIgnoreCase(value);
	}
	
	@Override	
	public void save(Customer customer) {
		customerRepository.save(customer);	
	};
	
	@Override	
	public void delete(Customer customer) {
		customerRepository.delete(customer);
	}
}
