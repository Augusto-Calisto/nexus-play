package br.com.tech.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "jogos")
public class Jogo implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	private long id;
	
	@Column(nullable = false)
	private String nome;
	
	@Column(nullable = true)
	private String website;
	
	@Column(nullable = false)
	private LocalDate dataLancamento;
	
	@Column(nullable = false, columnDefinition = "TEXT")
	private String descricao;
	
	@Column(nullable = false)
	private String urlImagem;
	
	@Column(nullable = false)
	private double media;
	
	@Column(nullable = false)
	private double tempo;
	
	@Column(nullable = false)
	private int quantidadeConquistas;
	
	@Column(nullable = false)
	private int quantidadeAvaliacao;
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(
		name = "jogos_plataformas", 
		joinColumns = {@JoinColumn(name = "jogos_id")}, 
		inverseJoinColumns = {@JoinColumn(name = "plataformas_id")}
	)
	private List<Plataforma> plataformas;
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(
		name = "jogos_generos",
		joinColumns = {@JoinColumn(name = "jogos_id")}, 
		inverseJoinColumns = {@JoinColumn(name = "generos_id")}
	)
	private List<Genero> generos;
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(
		name = "jogos_modos", 
		joinColumns = {@JoinColumn(name = "jogos_id")}, 
		inverseJoinColumns = {@JoinColumn(name = "modos_id")}
	)
	private List<Modo> modos;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "jogos_id")
	private List<Imagem> imagens;
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(
		name = "jogos_empresas_publicadoras", 
		joinColumns = {@JoinColumn(name = "jogos_id")}, 
		inverseJoinColumns = {@JoinColumn(name = "empresas_publicadoras_id")}
	)
	private List<EmpresaPublicadora> publicadores;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "jogos_id")
	private List<EmpresaDesenvolvedora> desenvolvedores;
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(
		name = "jogos_lojas", 
		joinColumns = {@JoinColumn(name = "jogos_id")}, 
		inverseJoinColumns = {@JoinColumn(name = "lojas_fornecedoras_id")}
	)
	private List<EmpresaFornecedora> fornecedores;
	
	// Relacionamento ManyToMany com ColecaoGamer mappedBy indica que a entidade ColecaoGamer é a proprietária do relacionamento e gerencia a tabela de junção.
    @ManyToMany(mappedBy = "jogos")
    private List<ColecaoGamer> colecoes;
	
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
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
		this.website = website;
	}

	public LocalDate getDataLancamento() {
		return dataLancamento;
	}

	public void setDataLancamento(LocalDate dataLancamento) {
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
		return media;
	}

	public void setMedia(double media) {
		this.media = media;
	}

	public List<Plataforma> getPlataformas() {
		return plataformas;
	}

	public void setPlataformas(List<Plataforma> plataformas) {
		this.plataformas = plataformas;
	}

	public List<Genero> getGeneros() {
		return generos;
	}

	public void setGeneros(List<Genero> generos) {
		this.generos = generos;
	}

	public List<Modo> getModos() {
		return modos;
	}

	public void setModos(List<Modo> modos) {
		this.modos = modos;
	}

	public List<Imagem> getImagens() {
		return imagens;
	}

	public void setImagens(List<Imagem> imagens) {
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

	public List<EmpresaPublicadora> getPublicadores() {
		return publicadores;
	}

	public void setPublicadores(List<EmpresaPublicadora> empresasCriadoras) {
		this.publicadores = empresasCriadoras;
	}

	public List<EmpresaFornecedora> getFornecedores() {
		return fornecedores;
	}

	public void setFornecedores(List<EmpresaFornecedora> lojas) {
		this.fornecedores = lojas;
	}

	public int getQuantidadeConquistas() {
		return quantidadeConquistas;
	}

	public void setQuantidadeConquistas(int quantidadeConquistas) {
		this.quantidadeConquistas = quantidadeConquistas;
	}
	
	public List<ColecaoGamer> getColecoes() {
		return colecoes;
	}

	public void setColecoes(List<ColecaoGamer> colecoes) {
		this.colecoes = colecoes;
	}

	public List<EmpresaDesenvolvedora> getDesenvolvedores() {
		return desenvolvedores;
	}

	public void setDesenvolvedores(List<EmpresaDesenvolvedora> desenvolvedores) {
		this.desenvolvedores = desenvolvedores;
	}
}