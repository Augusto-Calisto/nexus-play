package br.com.tech.entity;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "colecoes_gamer")
public class ColecaoGamer implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(nullable = false)
	private String nome;
	
	@Column(nullable = true)
	private String descricao;
	
	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.REFRESH }) // Define que operações serão propagadas
    @JoinTable(
        name = "colecoes_jogos", // Nome da tabela intermediária
        joinColumns = @JoinColumn(name = "colecoes_gamer_id"), // Coluna que referencia ColecaoGamer
        inverseJoinColumns = @JoinColumn(name = "jogos_id") // Coluna que referencia Jogo
    )
	private List<Jogo> jogos;

	@ManyToOne
    @JoinColumn(name = "usuarios_id")
	private Usuario usuario;

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
	
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public List<Jogo> getJogos() {
		return jogos;
	}

	public void setJogos(List<Jogo> jogos) {
		this.jogos = jogos;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
}