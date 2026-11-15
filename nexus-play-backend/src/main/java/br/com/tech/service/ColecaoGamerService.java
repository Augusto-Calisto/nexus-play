package br.com.tech.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.tech.dto.request.PersistirColecaoGamer;
import br.com.tech.entity.ColecaoGamer;
import br.com.tech.entity.Usuario;
import br.com.tech.repository.ColecaoGamerRepository;
import br.com.tech.repository.UsuarioRepository;

@Service
public class ColecaoGamerService {
	private final ColecaoGamerRepository colecaoGamerRepository;
	private final UsuarioRepository usuarioRepository;

	public ColecaoGamerService(ColecaoGamerRepository colecaoGamerRepository, UsuarioRepository usuarioRepository) {
		this.colecaoGamerRepository = colecaoGamerRepository;
		this.usuarioRepository = usuarioRepository;
	}
	
	public Page<ColecaoGamer> buscarColecoesGamerUsuario(String usuarioId, Pageable pageable) {
		return colecaoGamerRepository.buscarColecoesUsuarioId(usuarioId, pageable);
	}
	
	public ColecaoGamer persistirColecao(PersistirColecaoGamer persistirColecao) {
		ColecaoGamer colecaoGamer = new ColecaoGamer();
		
		if (persistirColecao.idColecao() > 0) {
			colecaoGamer.setId(persistirColecao.idColecao());
			
			Optional<Usuario> optional = usuarioRepository.findById(persistirColecao.idUsuario());
			
			Usuario usuario = optional.orElseThrow(() -> new UsernameNotFoundException("Usuario não encontrado"));
			
			colecaoGamer.setUsuario(usuario);
			
		} else {
			Usuario usuario = new Usuario();
			
			usuario.setId(persistirColecao.idUsuario());
			
			colecaoGamer.setUsuario(usuario);
		}
		
		colecaoGamer.setNome(persistirColecao.nome());
		
		colecaoGamer.setDescricao(persistirColecao.descricao());
		
		return colecaoGamerRepository.save(colecaoGamer);
	}
	
	public void excluirColecaoGamerId(int colecaoId) {
        colecaoGamerRepository.deleteById(colecaoId);
	}
}