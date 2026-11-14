package br.com.tech.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.tech.entity.ColecaoGamer;

public interface ColecaoGamerRepository extends JpaRepository<ColecaoGamer, Integer> {

	@Query(value ="SELECT cg FROM ColecaoGamer cg WHERE cg.usuario.id = :usuarioId")
	Page<ColecaoGamer> buscarColecoesUsuarioId(@Param(value = "usuarioId") String usuarioId, Pageable pageable);
}