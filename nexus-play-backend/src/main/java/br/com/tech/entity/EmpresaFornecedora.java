package br.com.tech.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "lojas_fornecedoras")
public class EmpresaFornecedora implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Integer id;
	
	@Column(unique = true, nullable = false)
	private Integer idLoja;
	
	@Column(nullable = false)
	private String nome;
	
	@Column
	private String urlDominio;
	
	@Column
	private String urlImagemLoja;

	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIdLoja() {
		return idLoja;
	}

	public void setIdLoja(Integer idLoja) {
		this.idLoja = idLoja;
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