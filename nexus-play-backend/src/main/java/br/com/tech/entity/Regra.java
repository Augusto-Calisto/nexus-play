package br.com.tech.entity;

import org.springframework.security.core.GrantedAuthority;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "regras")
public class Regra implements GrantedAuthority {
	private static final long serialVersionUID = 1L;

	@Id
	private Integer id;

	@Column(nullable = false, length = 25)
	private String nome;
	
	public Regra() {}

	public Regra(String nome) {
		this.nome = nome;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Override
	public String getAuthority() {
		return "ROLE_" + nome.toUpperCase();
	}

	@Override
	public String toString() {
		return nome.toUpperCase();
	}
}