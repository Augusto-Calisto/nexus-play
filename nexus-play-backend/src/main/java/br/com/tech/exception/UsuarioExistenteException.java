package br.com.tech.exception;

public class UsuarioExistenteException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public UsuarioExistenteException(String mensagem) { 
		super(mensagem);
	}
}