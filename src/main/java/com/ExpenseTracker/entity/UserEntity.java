package com.ExpenseTracker.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class UserEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String name;
	
	private String email;
	
	@OneToMany(mappedBy = "user")
	@JsonManagedReference
	private List<TrackerEntity> expenses;
	
	public UserEntity(String name, String email) {
		this.name = name;
		this.email = email;
	}
	
	public UserEntity() {
		
	}

	public List<TrackerEntity> getExpenses() {
		return expenses;
	}

	public void setExpenses(List<TrackerEntity> expenses) {
		this.expenses = expenses;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
