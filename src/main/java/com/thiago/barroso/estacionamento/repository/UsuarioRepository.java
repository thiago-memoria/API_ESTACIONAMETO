package com.thiago.barroso.estacionamento.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.thiago.barroso.estacionamento.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
