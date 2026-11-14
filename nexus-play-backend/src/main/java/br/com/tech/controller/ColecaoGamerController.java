package br.com.tech.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.tech.dto.request.PersistirColecaoGamer;
import br.com.tech.dto.response.ColecaoGamerDTO;
import br.com.tech.dto.response.PaginacaoColecaoGamer;
import br.com.tech.entity.ColecaoGamer;
import br.com.tech.mapper.ColecaoGamerMapper;
import br.com.tech.service.ColecaoGamerService;

@RestController
@RequestMapping(value = "/colecao")
public class ColecaoGamerController {	
	private ColecaoGamerService colecaoGamerService;

	public ColecaoGamerController(ColecaoGamerService colecaoGamerService) {
		this.colecaoGamerService = colecaoGamerService;
	}
	
	@GetMapping(value = "/usuario/{id}")
	public ResponseEntity<PaginacaoColecaoGamer> buscarTodasColecoesGamerUsuario(@PathVariable(value = "id") String usuarioId, @RequestParam(value = "numero-pagina", defaultValue = "0") int numeroPagina, @RequestParam(value = "quantidade-pagina", defaultValue = "0") int quantidadePagina) {		
		Page<ColecaoGamer> pageColecoes = colecaoGamerService.buscarColecoesGamerUsuario(usuarioId, PageRequest.of(numeroPagina, quantidadePagina));
		
		List<ColecaoGamerDTO> colecoesGamerDTO = pageColecoes.getContent().stream()
														.map(ColecaoGamerMapper::toDTO)
														.collect(Collectors.toList());
		
		PaginacaoColecaoGamer paginacao = new PaginacaoColecaoGamer();
		
		paginacao.setColecoes(colecoesGamerDTO);
		
		paginacao.setNumeroPagina(pageColecoes.getNumber());
		
		paginacao.setQuantidadePagina(pageColecoes.getNumberOfElements());
		
		paginacao.setTotal(pageColecoes.getTotalElements());
		
		paginacao.setTotalPaginas(pageColecoes.getTotalPages());
		
		return ResponseEntity.status(HttpStatus.OK).body(paginacao);
	}
	
	@PostMapping(value = "/criar")
	public ResponseEntity<String> cadastrarColecaoGamer(@RequestBody PersistirColecaoGamer colecaoGamer) {		
		ColecaoGamer colecaoGamerCadastrada = colecaoGamerService.persistirColecao(colecaoGamer);
				
		return ResponseEntity.status(HttpStatus.CREATED).body("Coleção '" + colecaoGamerCadastrada.getNome() + "' foi criada com sucesso");
	}
}