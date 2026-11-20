package br.com.tech.api.rawg.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

public class EmpresaFornecedoraJogoRawg implements Serializable {
	private static final long serialVersionUID = 1L;

	@JsonAlias(value = "id")
	private int id;
	
	@JsonAlias(value = "store")
	private Loja loja;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Loja getLoja() {
		return loja;
	}

	public void setLoja(Loja loja) {
		this.loja = loja;
	}
}

class Loja implements Serializable {
	private static final long serialVersionUID = 1L;

	@JsonAlias(value = "id")
	private int id;
	
	@JsonAlias(value = "name")
	private String nome;
	
	@JsonAlias(value = "domain")
	@JsonProperty(value = "site")
	private String urlDominio;
	
	@JsonAlias(value = "image_background")
	@JsonProperty(value = "urlImagem")
	private String urlImagemLoja;

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

	public String getUrlDominio() {
		return urlDominio;
	}

	public void setUrlDominio(String urlDominio) {
		this.urlDominio = urlDominio;
	}

	public String getUrlImagemLoja() {
		return urlImagemLoja;
	}

	public void setUrlImagemLoja(String urlImagemLoja) {
		this.urlImagemLoja = urlImagemLoja;
	}
}