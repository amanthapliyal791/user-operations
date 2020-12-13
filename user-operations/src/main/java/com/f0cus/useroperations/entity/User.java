package com.f0cus.useroperations.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

@Entity
public class User {
	@Id
	@GeneratedValue
	private int id;
	
	@Size(min=2,max=50,message="Name should be between 2 and 50 characters")
	private String name;
	
	@Past(message="Date Of Birth cannot be a future date")
	@Column(name="DOB")
	private LocalDate dateOfBirth;//in h2 snakeCase is always represented by "_" , i.e, DATE_OF_BIRTH

	public User() {
		super();
	}

	public User(int id, String name, LocalDate dateOfbirth) {
		super();
		this.id = id;
		this.name = name;
		this.dateOfBirth = dateOfbirth;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getDateOfbirth() {
		return dateOfBirth;
	}

	public void setDateOfbirth(LocalDate dateOfbirth) {
		this.dateOfBirth = dateOfbirth;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", dateOfBirth=" + dateOfBirth + "]";
	}
	
	
}
