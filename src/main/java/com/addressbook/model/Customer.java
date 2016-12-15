package com.addressbook.model;


import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Customer{

	@Id
	@GeneratedValue
	public long id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String phone;

	@Column(nullable = false)
	private String email;

	@ManyToMany(mappedBy = "customers", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
	private Set<User> users;

	protected Customer() {
	}

	public Customer(String name, String phone, String email) {
		this.name = name;
		this.phone = phone;
		this.email = email;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
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

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Customer customer = (Customer) obj;
		return id == customer.getId();
	}
	
	@Override
	public int hashCode(){
        int hashcode = 0;
        hashcode += name.length();
        hashcode += email.length();
        return hashcode;
    }
	
	@Override
	public String toString() {
		return String.format(
				"Customer[id=%s, name='%s', email='%s', phone='%s']",
				id, name, email, phone);
	}
}
