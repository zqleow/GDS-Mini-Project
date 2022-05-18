package com.gds.miniproject.MemberRestService.controller;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document("ClubMember")
public class DisplayClubMember {
	
	@Field(value = "name")
	private String name;
	
	@Field(value = "salary")
	private double salary;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

}
