package br.com.tech.exception;

public class RegraNaoExisteException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public RegraNaoExisteException(String mensagem) {
		super(mensagem);
	}
}
