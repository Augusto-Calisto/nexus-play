package br.com.tech.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.tech.api.rawg.model.JogoRawg;
import br.com.tech.api.rawg.model.PaginacaoJogoRawg;
import br.com.tech.api.rawg.service.RawgApiService;

@RestController
@RequestMapping(value = "/jogos")
public class JogoController {
	private final RawgApiService rawgApiService;
	
	public JogoController(RawgApiService rawgApiService) {
		this.rawgApiService = rawgApiService;
	}
	
	@GetMapping
	public ResponseEntity<PaginacaoJogoRawg> buscarTodosJogos(@RequestParam(value="numero-pagina", defaultValue="1", required = true) int numeroDaPagina, @RequestParam(value="quantidade-pagina", defaultValue="10", required = false) int quantidadeJogosPorPagina) {				
		PaginacaoJogoRawg jogosRawg = rawgApiService.buscarTodosJogos(numeroDaPagina, quantidadeJogosPorPagina);
		
		jogosRawg.setQuantidadePagina(quantidadeJogosPorPagina);
		
		jogosRawg.setNumeroPagina(numeroDaPagina);
		
		int totalPaginas = (int) jogosRawg.getTotalJogos() / quantidadeJogosPorPagina;
		
		jogosRawg.setTotalPaginas(totalPaginas);
		
		return ResponseEntity.status(HttpStatus.OK).body(jogosRawg);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<JogoRawg> buscarInformacoesJogoPeloId(@PathVariable int id) {		
		JogoRawg jogoRawg = rawgApiService.buscarJogoPeloId(id);
				
		return ResponseEntity.status(HttpStatus.OK).body(jogoRawg);
	}
	
	@GetMapping(value = "/nome")
	public ResponseEntity<PaginacaoJogoRawg> buscarJogoPeloNome(@RequestParam(value="jogo") String nomeJogo, @RequestParam(value="numero-pagina", defaultValue="1", required = true) int numeroDaPagina, @RequestParam(value="quantidade-pagina", defaultValue="10", required=false) int quantidadeJogosPorPagina) {
		PaginacaoJogoRawg jogosEncontrados = rawgApiService.buscarJogoPeloNome(nomeJogo, numeroDaPagina, quantidadeJogosPorPagina);
				
		long quantidadeJogos = jogosEncontrados.getTotalJogos();
		
		jogosEncontrados.setNumeroPagina(numeroDaPagina);
		
		jogosEncontrados.setQuantidadePagina(quantidadeJogosPorPagina);
		
		int totalPaginas = (int) quantidadeJogos / quantidadeJogosPorPagina;
		
		jogosEncontrados.setTotalPaginas(totalPaginas);
		
		return ResponseEntity.status(HttpStatus.OK).body(jogosEncontrados);
	}
}