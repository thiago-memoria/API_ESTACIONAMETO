package com.thiago.barroso.estacionamento.jwt;

public class JwtToken {
	
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	private String token;

	public JwtToken(String token) {
		super();
		this.token = token;
	}

	public JwtToken() {
		super();
	}
	
}
