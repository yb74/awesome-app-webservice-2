package com.example.demo.dto;

public class UserInfoDTO {
	private int id;
	private String name;
	private String email;
	private String roles;
	
	public UserInfoDTO(int id, String name, String email, String roles) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.roles = roles;
	}
	public UserInfoDTO() {
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
}
