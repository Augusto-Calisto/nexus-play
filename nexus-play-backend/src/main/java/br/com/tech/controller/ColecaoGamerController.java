package br.com.tech.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.tech.dto.request.PersistirColecaoGamer;
import br.com.tech.entity.ColecaoGamer;
import br.com.tech.service.ColecaoGamerService;

@RestController
@RequestMapping(value = "/colecao")
public class ColecaoGamerController {	
	private ColecaoGamerService colecaoGamerService;

	public ColecaoGamerController(ColecaoGamerService colecaoGamerService) {
		this.colecaoGamerService = colecaoGamerService;
	}
	
	@PostMapping(value = "/criar")
	public ResponseEntity<String> cadastrarColecaoGamer(@RequestBody PersistirColecaoGamer colecaoGamer) {		
		ColecaoGamer colecaoGamerCadastrada = colecaoGamerService.persistirColecao(colecaoGamer);
				
		return ResponseEntity.status(HttpStatus.CREATED).body("Coleção '" + colecaoGamerCadastrada.getNome() + "' foi criada com sucesso");
	}
}