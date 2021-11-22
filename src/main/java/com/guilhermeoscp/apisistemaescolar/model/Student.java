package com.guilhermeoscp.apisistemaescolar.model;

import javax.persistence.Entity;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Entity
public class Student extends AbstractEntity {
	private static final long serialVersionUID = 1L;
	
	@NotEmpty(message = "The student name field is mandatory")
	private String name;

	@NotEmpty
	@Email
	private String email;
	
	public String getName() {
		return name;
	}
	
	public String getEmail() {
		return email;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}	
}
