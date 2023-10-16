package com.example.demo.dto;

public class UserCreateDTO {
	private int id;
	private String name;
	private String email;
	private String roles;
	private String password;
	
	public UserCreateDTO(int id, String name, String email, String roles, String password) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.roles = roles;
		this.password = password;
	}
	
	public UserCreateDTO() {
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
