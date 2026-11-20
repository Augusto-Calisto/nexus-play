package br.com.tech.api.rawg.model;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PaginacaoJogoRawg implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @JsonAlias(value = "count")
    @JsonProperty(value = "total")
    private long totalJogos;
    
    @JsonAlias(value = "results")
    @JsonProperty(value = "jogos")
    private List<JogoRawg> jogos;
    
    @JsonProperty(value = "numeroPagina")
    private int numeroPagina;
    
    @JsonProperty(value = "quantidadePagina")
    private int quantidadePagina;
    
    @JsonProperty(value = "totalPaginas")
    private int totalPaginas;


	public long getTotalJogos() {
		return totalJogos;
	}

	public void setTotalJogos(long totalJogos) {
		this.totalJogos = totalJogos;
	}

	public List<JogoRawg> getJogos() {
		return jogos;
	}

	public void setJogos(List<JogoRawg> jogos) {
		this.jogos = jogos;
	}

	public int getNumeroPagina() {
		return numeroPagina;
	}

	public void setNumeroPagina(int numeroPagina) {
		this.numeroPagina = numeroPagina;
	}

	public int getQuantidadePagina() {
		return quantidadePagina;
	}

	public void setQuantidadePagina(int quantidadePagina) {
		this.quantidadePagina = quantidadePagina;
	}

	public int getTotalPaginas() {
		return totalPaginas;
	}

	public void setTotalPaginas(int totalPaginas) {
		this.totalPaginas = totalPaginas;
	}
}