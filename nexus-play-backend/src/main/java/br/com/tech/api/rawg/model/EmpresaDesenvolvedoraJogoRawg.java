package br.com.tech.api.rawg.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonAlias;

public class EmpresaDesenvolvedoraJogoRawg implements Serializable {
	private static final long serialVersionUID = 1L;

	@JsonAlias(value = "id")
	private int id;
	
	@JsonAlias(value = "name")
	private String nome;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
}