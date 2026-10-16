package br.com.tech.exception;

import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;

public class UsuarioExistenteException extends OAuth2AuthenticationException {
	private static final long serialVersionUID = 1L;
	
	public UsuarioExistenteException(String mensagem) {
		super(new OAuth2Error("conta_ja_cadastrada", mensagem, null), mensagem);
	}
}