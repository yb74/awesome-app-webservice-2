package com.example.demo.dto;

public class RefreshTokenRequestDTO {
	 private String token;

	public RefreshTokenRequestDTO() {
	}
	
	public RefreshTokenRequestDTO(String token) {
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
