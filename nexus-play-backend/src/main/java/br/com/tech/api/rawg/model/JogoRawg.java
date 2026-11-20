package br.com.tech.api.rawg.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.tech.entity.EmpresaDesenvolvedora;
import br.com.tech.entity.EmpresaFornecedora;
import br.com.tech.entity.EmpresaPublicadora;
import br.com.tech.entity.Genero;
import br.com.tech.entity.Jogo;
import br.com.tech.entity.Modo;
import br.com.tech.entity.Plataforma;

public class JogoRawg implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@JsonAlias(value = "id")
	private int id;
	
	@JsonAlias(value = "name")
	private String nome;
	
	@JsonAlias(value = "website")
	@JsonProperty(value = "urlWebsite")
	private String website;
	
	@JsonAlias(value = "released")
	private String dataLancamento;
	
	@JsonAlias(value = "description")
	private String descricao;
	
	@JsonAlias(value = "background_image")
	@JsonProperty(value = "urlImagem")
	private String urlImagem;
	
	@JsonAlias(value = "rating")
	@JsonProperty(value = "mediaGeral")
	private double media;
	
	@JsonAlias(value = "playtime")
	@JsonProperty(value = "tempo")
	private double tempo;
	
	@JsonAlias(value = "achievements_count")
	@JsonProperty(value = "totalConquistas")
	private int quantidadeConquistas;
	
	@JsonAlias(value = "reviews_count")
	@JsonProperty(value = "quantidadeAvaliacao")
	private int quantidadeAvaliacao;
	
	@JsonAlias(value = "platforms")
	private List<PlataformaJogoRawg> plataformas;
	
	@JsonAlias(value = "genres")
	private List<GeneroJogoRawg> generos;
	
	@JsonAlias(value = "tags")
	private List<ModoJogoRawg> modos;
	
	@JsonAlias(value = "short_screenshots")
	private List<ImagemJogoRawg> imagens;
	
	@JsonAlias(value = "publishers")
	@JsonProperty(value = "publicadoras")
	private List<EmpresaPublicadoraJogoRawg> publicadores;
	
	@JsonAlias(value = "developers")
	@JsonProperty(value = "desenvolvedores")
	private List<EmpresaDesenvolvedoraJogoRawg> desenvolvedores;
	
	@JsonAlias(value = "stores")
	@JsonProperty(value = "fornecedoras")
	private List<EmpresaFornecedoraJogoRawg> fornecedores;
	
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
	
	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		if(website != null) {
			if(website.isBlank()) {
				this.website = null;
			} else {
				this.website = website;
			}
		} else {
			this.website = website;
		}
	}

	public String getDataLancamento() {
		return dataLancamento;
	}

	public void setDataLancamento(String dataLancamento) {
		this.dataLancamento = dataLancamento;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getUrlImagem() {
		return urlImagem;
	}
	
	public void setUrlImagem(String urlImagemJogo) {		
		this.urlImagem = urlImagemJogo;
	}

	public double getMedia() {
		media *= 2;
		
		if(media > 10) {
			return media / 2;
		}
		
		return media;
	}

	public void setMedia(double media) {
		this.media = media;
	}

	public List<PlataformaJogoRawg> getPlataformas() {
		return plataformas;
	}

	public void setPlataformas(List<PlataformaJogoRawg> plataformas) {
		this.plataformas = plataformas;
	}

	public List<GeneroJogoRawg> getGeneros() {
		return generos;
	}

	public void setGeneros(List<GeneroJogoRawg> generos) {
		this.generos = generos;
	}

	public List<ModoJogoRawg> getModos() {
		return modos;
	}

	public void setModos(List<ModoJogoRawg> modos) {
		this.modos = modos;
	}

	public List<ImagemJogoRawg> getImagens() {
		return imagens;
	}

	public void setImagens(List<ImagemJogoRawg> imagens) {
		this.imagens = imagens;
	}

	public double getTempo() {
		return tempo;
	}

	public void setTempo(double tempo) {
		this.tempo = tempo;
	}

	public int getQuantidadeAvaliacao() {
		return quantidadeAvaliacao;
	}

	public void setQuantidadeAvaliacao(int quantidadeAvaliacao) {
		this.quantidadeAvaliacao = quantidadeAvaliacao;
	}

	public List<EmpresaPublicadoraJogoRawg> getPublicadores() {
		return publicadores;
	}

	public void setPublicadores(List<EmpresaPublicadoraJogoRawg> empresasCriadoras) {
		this.publicadores = empresasCriadoras;
	}

	public List<EmpresaFornecedoraJogoRawg> getFornecedores() {
		return fornecedores;
	}

	public void setFornecedores(List<EmpresaFornecedoraJogoRawg> fornecedoras) {
		this.fornecedores = fornecedoras;
	}

	public int getQuantidadeConquistas() {
		return quantidadeConquistas;
	}

	public void setQuantidadeConquistas(int quantidadeConquistas) {
		this.quantidadeConquistas = quantidadeConquistas;
	}

	public List<EmpresaDesenvolvedoraJogoRawg> getDesenvolvedores() {
		return desenvolvedores;
	}

	public void setDesenvolvedores(List<EmpresaDesenvolvedoraJogoRawg> desenvolvedores) {
		this.desenvolvedores = desenvolvedores;
	}
}