package br.com.tech.mapper;

import br.com.tech.dto.response.ColecaoGamerDTO;
import br.com.tech.entity.ColecaoGamer;

public class ColecaoGamerMapper {
	
	public static ColecaoGamerDTO toDTO(ColecaoGamer colecaoGamer) {
		ColecaoGamerDTO colecaoGamerDTO = new ColecaoGamerDTO(
			colecaoGamer.getId(),
			colecaoGamer.getNome(),
			colecaoGamer.getDescricao()
		);
		
		return colecaoGamerDTO;
	}
	
	public static ColecaoGamer toEntity(ColecaoGamerDTO colecaoGamerDTO) {
		ColecaoGamer colecaoGamer = new ColecaoGamer();
		
		colecaoGamer.setId(colecaoGamerDTO.id());
		
		colecaoGamer.setNome(colecaoGamerDTO.nome());
		
		colecaoGamer.setDescricao(colecaoGamerDTO.descricao());
		
		return colecaoGamer;
	}
}