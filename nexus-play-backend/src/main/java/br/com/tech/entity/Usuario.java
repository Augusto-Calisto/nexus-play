package br.com.tech.entity;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuarios")
public class Usuario implements UserDetails {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(length = 100)
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;
	
	@Column(nullable = false, length = 100)
	private String nome;
	
	@Column(nullable = false, length = 30)
	private String username;
	
	@Column(name = "senha", length = 100)
	private String password;
	
	@Column(unique = true, nullable = false, length = 50)
	private String email;
	
	@Column(name = "id_usuario_oauth", length = 80)
    private String provedorId;
	
	@Column(length = 45)
	private String nomeProvedor;
	
	@Column(name = "ativo", nullable = false)
	private Boolean isAtivo;
	
	@ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JoinTable(
		name = "usuarios_regras",
		joinColumns = @JoinColumn(name = "usuarios_id"),
		inverseJoinColumns = @JoinColumn(name = "regras_id")
	)
	private List<Regra> regras;
	
	@OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
	private List<ColecaoGamer> colecoesGamer;
	
	
	public Usuario() {}
	
	// Construtor Usuario e Senha
	public Usuario(String nome, String username, String email, List<Regra> regras) {
		this.nomeProvedor = "LOCAL";
		this.nome = nome;
		this.username = username;
		this.email = email;
		this.isAtivo = false;
		this.regras = regras;
	}
	
	// Construtor Usuario OAuth
	public Usuario(String nomeProvedor, String provedorId, String nome, String username, String email, List<Regra> regras) {
		this.nomeProvedor = nomeProvedor;
		this.provedorId = provedorId;
		this.nome = nome;
		this.username = username;
		this.email = email;
		this.isAtivo = true;
		this.regras = regras;
	}


	@PrePersist
	void preInsert() {
	   if(this.isAtivo == null) {
	       this.isAtivo = false;
	   }
	}
	
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return regras;
	}
	
	@Override
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	@Override
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return this.isAtivo && this.isAtivo != null;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getProvedorId() {
		return provedorId;
	}

	public void setProvedorId(String provedorId) {
		this.provedorId = provedorId;
	}

	public String getNomeProvedor() {
		return nomeProvedor;
	}

	public void setNomeProvedor(String nomeProvedor) {
		this.nomeProvedor = nomeProvedor;
	}

	public List<Regra> getRegras() {
		return regras;
	}

	public void setRegras(List<Regra> regras) {
		this.regras = regras;
	}

	public List<ColecaoGamer> getColecoesGamer() {
		return colecoesGamer;
	}

	public void setColecoesGamer(List<ColecaoGamer> listasGamer) {
		this.colecoesGamer = listasGamer;
	}

	public Boolean getIsAtivo() {
		return isAtivo;
	}

	public void setIsAtivo(Boolean isAtivo) {
		this.isAtivo = isAtivo;
	}
}