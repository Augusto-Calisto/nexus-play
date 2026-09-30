package br.com.tech.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "empresas_publicadoras")
public class EmpresaPublicadora implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	private int id;
	
	@Column(nullable = false)
	private String nome;
	
	@Column(nullable = false)
	private String urlImagemEmpresa;

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

	public String getUrlImagemEmpresa() {
		return urlImagemEmpresa;
	}

	public void setUrlImagemEmpresa(String urlImagemEmpresa) {
		this.urlImagemEmpresa = urlImagemEmpresa;
	}
}
