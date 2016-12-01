package com.addressbook.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "cutomers")
public class Customer {
	
	@Id
    public String id;
	private String name;
	private String phone;
	private String email;
	
	
	public Customer(String name, String phone, String email) {
		this.name = name;
		this.phone = phone;
		this.email = email;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	   @Override
	    public String toString() {
	        return String.format(
	                "Customer[id=%s, name='%s', email='%s', phone='%s']",
	                id, name, email, phone);
	    }
}
