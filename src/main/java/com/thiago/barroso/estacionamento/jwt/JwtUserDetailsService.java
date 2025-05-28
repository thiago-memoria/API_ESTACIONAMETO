package com.thiago.barroso.estacionamento.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.thiago.barroso.estacionamento.entity.Usuario;
import com.thiago.barroso.estacionamento.service.UsuarioService;

public class JwtUserDetailsService implements UserDetailsService{
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Override
	public UserDetails loadUserByUsername(String username) {
		Usuario usuario = usuarioService.buscarPorUsername(username);
		return new JwtUserDeatails(usuario);
	}
	
	public JwtToken getTokenAuthenticated(String username) {
		Usuario.Role role = usuarioService.buscarRolePorUsername(username);
		return JwtUtils.createToken(username, role.name().substring("ROLE_".length()));
	}
}
