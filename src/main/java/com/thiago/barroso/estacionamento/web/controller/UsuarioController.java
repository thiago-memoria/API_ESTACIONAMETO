package com.thiago.barroso.estacionamento.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thiago.barroso.estacionamento.entity.Usuario;
import com.thiago.barroso.estacionamento.service.UsuarioService;
import com.thiago.barroso.estacionamento.web.dto.UsuarioCreateDto;
import com.thiago.barroso.estacionamento.web.dto.UsuarioResponseDto;
import com.thiago.barroso.estacionamento.web.dto.UsuarioSenhaDto;
import com.thiago.barroso.estacionamento.web.exception.ErrorMessage;
import com.thiago.barroso.estacionamento.web.mapper.UsuarioMapper;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name="Usuarios", description = "Contém todas as operações relativas aos recursos para cadastro, edição e leitura de um usuário.")
@RestController
@RequestMapping("api/v1/usuarios")
public class UsuarioController {
	
	@Autowired
    private UsuarioService usuarioService;
	
	@Operation(
		    summary = "Criar um novo usuário",
		    description = "Recurso para criar um novo usuário",
		    responses = {
		        @ApiResponse(
		            responseCode = "201",
		            description = "Recurso Criado com sucesso",
		            content = @Content(
		                mediaType = "application/json",
		                schema = @Schema(implementation = UsuarioResponseDto.class)
		            )
		        ),
		        @ApiResponse(
			            responseCode = "409",
			            description = "Usuário e-mail já cadastrado no sistema.",
			            content = @Content(
			                mediaType = "application/json",
			                schema = @Schema(implementation = ErrorMessage.class)
			            )
			        ),
		        @ApiResponse(
			            responseCode = "422",
			            description = "Recurso não processado por dados de entrada inválidos.",
			            content = @Content(
			                mediaType = "application/json",
			                schema = @Schema(implementation = ErrorMessage.class)
			            )
			        )
		    }
		)
    @PostMapping
    public ResponseEntity<UsuarioResponseDto> create(@Valid @RequestBody UsuarioCreateDto createDto) {
        Usuario user = usuarioService.salvar(UsuarioMapper.toUsuario(createDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(UsuarioMapper.toDto(user));
    }

	@Operation(
		    summary = "Recuperar um usuário pelo id",
		    description = "Recuperar um usuário pelo id",
		    responses = {
		        @ApiResponse(
		            responseCode = "200",
		            description = "Recurso recuperado com sucesso",
		            content = @Content(
		                mediaType = "application/json",
		                schema = @Schema(implementation = UsuarioResponseDto.class))),
		        @ApiResponse(
			            responseCode = "404",
			            description = "Recurso não encontrado",
			            content = @Content(
			                mediaType = "application/json",
			                schema = @Schema(implementation = ErrorMessage.class)))
		        
		    })
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDto> getById(@PathVariable("id") Long id) {
        Usuario user = usuarioService.buscarPorId(id);
        return ResponseEntity.ok(UsuarioMapper.toDto(user));
    }
	
	@Operation(
		    summary = "Atualizar senha",
		    description = "Atualizar senha",
		    responses = {
		        @ApiResponse(
		            responseCode = "204",
		            description = "Senha atualizada com suceso",
		            content = @Content(
		                mediaType = "application/json",
		                schema = @Schema(implementation = Void.class))),
		        @ApiResponse(
			            responseCode = "404",
			            description = "Recurso não encontrado",
			            content = @Content(
			                mediaType = "application/json",
			                schema = @Schema(implementation = ErrorMessage.class))),
		        @ApiResponse(
			            responseCode = "400",
			            description = "Senha não confere",
			            content = @Content(
			                mediaType = "application/json",
			                schema = @Schema(implementation = ErrorMessage.class)))
		        
		    })
    @PatchMapping("/{id}")
    public ResponseEntity<Void> updatePassword(@PathVariable Long id, @Valid UsuarioSenhaDto dto) {
        Usuario user = usuarioService.editarSenha(id, dto.getSenhaAtual(), dto.getNovaSenha(), dto.getConfirmaSenha());
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponseDto>> getAll() {
        List<Usuario> users = usuarioService.buscarTodos();
        return ResponseEntity.ok(UsuarioMapper.toListDto(users));
    }
}

