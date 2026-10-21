package br.com.tech.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.tech.entity.Regra;

public interface RegraRepository extends JpaRepository<Regra, Integer> {
	Optional<Regra> findByNome(String nome);
}
