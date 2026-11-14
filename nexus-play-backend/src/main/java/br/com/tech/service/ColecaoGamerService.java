package br.com.tech.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.tech.dto.request.PersistirColecaoGamer;
import br.com.tech.entity.ColecaoGamer;
import br.com.tech.entity.Usuario;
import br.com.tech.repository.ColecaoGamerRepository;

@Service
public class ColecaoGamerService {
	private ColecaoGamerRepository colecaoGamerRepository;

	public ColecaoGamerService(ColecaoGamerRepository colecaoGamerRepository) {
		this.colecaoGamerRepository = colecaoGamerRepository;
	}
	
	public Page<ColecaoGamer> buscarColecoesGamerUsuario(String usuarioId, Pageable pageable) {
		return colecaoGamerRepository.buscarColecoesUsuarioId(usuarioId, pageable);
	}
	
	public ColecaoGamer persistirColecao(PersistirColecaoGamer persistirColecao) {
		ColecaoGamer colecaoGamer = new ColecaoGamer();
		
		if (persistirColecao.idColecao() > 0) {
			colecaoGamer.setId(persistirColecao.idColecao());
		}
		
		colecaoGamer.setNome(persistirColecao.nome());
		
		colecaoGamer.setDescricao(persistirColecao.descricao());
		
		Usuario usuario = new Usuario();
		
		usuario.setId(persistirColecao.idUsuario());
		
		colecaoGamer.setUsuario(usuario);
		
		return colecaoGamerRepository.save(colecaoGamer);
	}
}