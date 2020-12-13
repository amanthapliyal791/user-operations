package com.f0cus.useroperations.entity;

import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class User {
	@Id
	@GeneratedValue
	private int id;
	
	private String name;
	
	private LocalDate dateOfbirth;

	public User() {
		super();
	}

	public User(int id, String name, LocalDate dateOfbirth) {
		super();
		this.id = id;
		this.name = name;
		this.dateOfbirth = dateOfbirth;
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
		return dateOfbirth;
	}

	public void setDateOfbirth(LocalDate dateOfbirth) {
		this.dateOfbirth = dateOfbirth;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", dateOfbirth=" + dateOfbirth + "]";
	}
	
	
}
