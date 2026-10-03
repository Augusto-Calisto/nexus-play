package br.com.tech.dto.response;

import java.util.List;

public record UsuarioInfoResponse(String idUsuario, String nome, String username, String email, List<String> regras) {}
