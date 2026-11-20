package br.com.tech.api.rawg.model;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PaginacaoImagemJogoRawg implements Serializable {
	private static final long serialVersionUID = 1L;

	@JsonAlias(value = "count")
    @JsonProperty(value = "total")
    private int totalImagens;
    
    @JsonAlias(value = "results")
    @JsonProperty(value = "imagens")
    private List<ImagemJogoRawg> imagens;
    
    @JsonAlias(value = "previous")
    @JsonProperty(value = "urlProximaListagemImagens")
    private String urlProximaListagemImagens;
    
    @JsonAlias(value = "previous")
    @JsonProperty(value = "urlListagemAnteriorImagens")
    private String urlListagemAnteriorImagens;

	public int getTotalImagens() {
		return totalImagens;
	}

	public void setTotalImagens(int totalImagens) {
		this.totalImagens = totalImagens;
	}

	public List<ImagemJogoRawg> getImagens() {
		return imagens;
	}

	public void setImagens(List<ImagemJogoRawg> imagens) {
		this.imagens = imagens;
	}

	public String getUrlProximaListagemImagens() {
		return urlProximaListagemImagens;
	}

	public void setUrlProximaListagemImagens(String urlProximaListagemImagens) {
		this.urlProximaListagemImagens = urlProximaListagemImagens;
	}

	public String getUrlListagemAnteriorImagens() {
		return urlListagemAnteriorImagens;
	}

	public void setUrlListagemAnteriorImagens(String urlListagemAnteriorImagens) {
		this.urlListagemAnteriorImagens = urlListagemAnteriorImagens;
	}
}