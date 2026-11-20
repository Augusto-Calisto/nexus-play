package br.com.tech.api.rawg.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ImagemJogoRawg implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @JsonAlias(value = "id")
	private int id;
	
	@JsonAlias(value = "image")
	@JsonProperty(value = "url")
	private String urlImagem;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUrlImagem() {
		return urlImagem;
	}

	public void setUrlImagem(String urlImagem) {
		this.urlImagem = urlImagem;
	}
}