package com.thiago.barroso.estacionamento.jwt;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

import com.thiago.barroso.estacionamento.entity.Usuario;

public class JwtUserDeatails extends User{

	private static final long serialVersionUID = 1L;
	
	private Usuario usuario;
	
	public JwtUserDeatails(Usuario usuario) {
		super(usuario.getUsername(), usuario.getPassword(), AuthorityUtils.createAuthorityList(usuario.getRole().name()));
		this.usuario = usuario;	
	}
	
	public Long getId() {
		return this.usuario.getId();
	}
	
	public String getRole() {
		return this.usuario.getRole().name();
	}
}
