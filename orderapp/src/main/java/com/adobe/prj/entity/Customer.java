package com.adobe.prj.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="customers")
@Entity
public class Customer {
	
	@Id
	private String email;
	
	@Column(name="first_name")
	private String firstName;

	
	public Customer() {
		super();
	}

	
	public Customer(String email, String firstName) {
		super();
		this.email = email;
		this.firstName = firstName;
	}


	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	
}