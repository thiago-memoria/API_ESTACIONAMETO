package com.thiago.barroso.estacionamento.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thiago.barroso.estacionamento.jwt.JwtToken;
import com.thiago.barroso.estacionamento.jwt.JwtUserDetailsService;
import com.thiago.barroso.estacionamento.web.dto.UsuarioLoginDto;
import com.thiago.barroso.estacionamento.web.exception.ErrorMessage;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/v1")
public class AutenticacaoController {
	
	@Autowired
	private JwtUserDetailsService detailsService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	public ResponseEntity<?> autenticar(@RequestBody @Valid UsuarioLoginDto dto, HttpServletRequest request){
		 try {
			 UsernamePasswordAuthenticationToken authenticationToken = 
					 new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword());
			 authenticationManager.authenticate(authenticationToken);
			 JwtToken token = detailsService.getTokenAuthenticated(dto.getUsername());
			 return ResponseEntity.ok(token);
		 }catch(AuthenticationException ex) {
			 
		 }
		 return ResponseEntity
				 .badRequest()
				 .body(new ErrorMessage(request, HttpStatus.BAD_REQUEST, "Credenciais inválidas"));
	}
	
	
}
