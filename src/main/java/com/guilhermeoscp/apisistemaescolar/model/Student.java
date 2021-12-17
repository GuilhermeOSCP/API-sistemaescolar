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
	@Email(message = "Enter a valid email")
	private String email;
	
	public Student() {
	}

	public Student(@NotEmpty(message = "The student name field is mandatory") String name,
			@NotEmpty @Email String email) {
		super();
		this.name = name;
		this.email = email;
	}

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
	
	@Override
	public String toString() {
		return "Student [name=" + name + ", email=" + email + "]";
	}
}
