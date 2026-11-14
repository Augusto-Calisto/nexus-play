package br.com.tech.dto.response;

import java.io.Serializable;
import java.util.List;

public class PaginacaoColecaoGamer implements Serializable {
	private static final long serialVersionUID = 1L;

	private List<ColecaoGamerDTO> colecoes;
	private int numeroPagina;
	private long total;
	private int quantidadePagina;
	private int totalPaginas;

	public List<ColecaoGamerDTO> getColecoes() {
		return colecoes;
	}

	public void setColecoes(List<ColecaoGamerDTO> colecoes) {
		this.colecoes = colecoes;
	}

	public int getNumeroPagina() {
		return numeroPagina;
	}

	public void setNumeroPagina(int numeroPagina) {
		this.numeroPagina = numeroPagina;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
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