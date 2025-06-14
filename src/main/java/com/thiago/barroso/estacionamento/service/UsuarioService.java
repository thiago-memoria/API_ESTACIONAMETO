package com.thiago.barroso.estacionamento.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thiago.barroso.estacionamento.entity.Usuario;
import com.thiago.barroso.estacionamento.entity.Usuario.Role;
import com.thiago.barroso.estacionamento.exception.EntityNotFoundException;
import com.thiago.barroso.estacionamento.exception.UsernameUniqueViolationException;
import com.thiago.barroso.estacionamento.repository.UsuarioRepository;

@Service
public class UsuarioService {
	
	@Autowired
    private UsuarioRepository usuarioRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

    @Transactional
    public Usuario salvar(Usuario usuario) {
    	try {
    		usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
    		return usuarioRepository.save(usuario);
    	}catch(org.springframework.dao.DataIntegrityViolationException ex){
    		throw new UsernameUniqueViolationException(String.format("Username {%s} já cadastrado.", usuario.getUsername()));
    	}
    }

    @Transactional(readOnly = true)
    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Usuário id=%s não encontrado", id))
        );
    }

    @Transactional
    public Usuario editarSenha(Long id, String senhaAtual, String novaSenha, String confirmaSenha) {
        if (!novaSenha.equals(confirmaSenha)) {
            throw new RuntimeException("Nova senha não confere com confirmação de senha.");
        }

        Usuario user = buscarPorId(id);
        if (!passwordEncoder.matches(senhaAtual, user.getPassword())) {
            throw new RuntimeException("Sua senha não confere.");
        }

        user.setPassword(passwordEncoder.encode(novaSenha));
        return user;
    }

    @Transactional(readOnly = true)
    public List<Usuario> buscarTodos() {
        return usuarioRepository.findAll();
    }
    
    @Transactional(readOnly = true)
    public Usuario buscarPorUsername(String username) {
    	return usuarioRepository.findByUsername(username).orElseThrow(
    			() -> new EntityNotFoundException(String.format("Usuario com '%s' não encontrado", username))
    	);
    }
    
    @Transactional(readOnly = true)
	public Role buscarRolePorUsername(String username) {
		return usuarioRepository.findRoleByUsername(username);
	}
}