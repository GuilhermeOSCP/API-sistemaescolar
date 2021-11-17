package com.guilhermeoscp.apisistemaescolar.model;

import javax.persistence.Entity;

@Entity
public class Student extends AbstractEntity {
	private static final long serialVersionUID = 1L;
	
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
