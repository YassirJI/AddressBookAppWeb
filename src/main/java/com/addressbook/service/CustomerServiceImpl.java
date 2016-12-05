package com.addressbook.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.addressbook.model.Customer;
import com.addressbook.repository.CustomerRepository;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerRepository customerRepository;
	
	@Override
	public List<Customer> findAll(){
		return customerRepository.findAll();
	}
	
	@Override
	public List<Customer> findCustomersBy(String criteria) {
		return customerRepository.findByNameOrPhoneOrEmailIgnoreCase(criteria);
	}
	
	@Override	
	public List<Customer> findByName(String name){
		return customerRepository.findByNameIgnoreCase(name);
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
