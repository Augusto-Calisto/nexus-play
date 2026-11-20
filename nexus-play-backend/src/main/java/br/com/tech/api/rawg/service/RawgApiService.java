package br.com.tech.api.rawg.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.tech.api.rawg.model.ImagemJogoRawg;
import br.com.tech.api.rawg.model.JogoRawg;
import br.com.tech.api.rawg.model.PaginacaoImagemJogoRawg;
import br.com.tech.api.rawg.model.PaginacaoJogoRawg;

@Service
public class RawgApiService {	
	private final RestTemplate restTemplate;
	
	@Value(value = "${rawg.api-key}")
	private String apiKey;
	
	public RawgApiService(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	@Cacheable(value = "jogos")
	public PaginacaoJogoRawg buscarTodosJogos(int numeroDaPagina, int quantidadeJogosPorPagina) {
		String urlJogos = String.format("/games?key=%s&page=%d&page_size=%d", apiKey, numeroDaPagina, quantidadeJogosPorPagina);
		
		ResponseEntity<PaginacaoJogoRawg> respostaRawg = restTemplate.getForEntity(urlJogos, PaginacaoJogoRawg.class);
		
		return respostaRawg.getBody();
	}

	@Cacheable(value = "jogo-id")
	public JogoRawg buscarJogoPeloId(int id) {
		String urlJogos = String.format("/games/%d?key=%s", id, apiKey);
		
		ResponseEntity<JogoRawg> respostaRawg = restTemplate.getForEntity(urlJogos, JogoRawg.class);
		
		return respostaRawg.getBody();
	}
	
	@Cacheable(value = "jogo-imagens")
	public List<ImagemJogoRawg> buscarImagensJogoPeloId(int id) {
		String urlJogos = String.format("/games/%d/screenshots?key=%s", id, apiKey);
		
		ResponseEntity<PaginacaoImagemJogoRawg> respostaRawg = restTemplate.getForEntity(urlJogos, PaginacaoImagemJogoRawg.class);
		
		PaginacaoImagemJogoRawg imagemRespostaDTO = respostaRawg.getBody();
		
		return imagemRespostaDTO.getImagens();
	}
	
	public PaginacaoJogoRawg buscarJogoPeloNome(String nomeJogo, int numeroDaPagina, int quantidadeJogosPorPagina) {
		String urlJogos = String.format("/games?key=%s&search=%s&search_precise=%s&page=%d&page_size=%d", apiKey, nomeJogo, "true", numeroDaPagina, quantidadeJogosPorPagina);
		
		ResponseEntity<PaginacaoJogoRawg> respostaRawg = restTemplate.getForEntity(urlJogos, PaginacaoJogoRawg.class);
		
		return respostaRawg.getBody();
	}
}