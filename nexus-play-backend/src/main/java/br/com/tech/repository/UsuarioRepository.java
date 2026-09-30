package br.com.tech.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.tech.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, String> {
	Optional<Usuario> findByEmail(String email);
}