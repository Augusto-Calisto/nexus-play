package br.com.tech.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CadastroUsuario (
	@NotBlank(message = "Nome é obrigatório")
	@Size(min = 3, message = "O 'Nome' precisa ter no minimo 3 caracteres")
	String nome,

	@NotBlank(message = "Username é obrigatório")
	@Size(min = 6, message = "O 'Username' precisa ter no minimo 6 caracteres")
	String username,
	
	@Email(message = "Email precisa conter '@'")
	String email,
	
	@NotBlank(message = "A senha não pode estar em branco")
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\\\"\\\\|,.<>/?]).{8,17}$", message = "A senha deve ter no mínimo 8 caracteres | maximo 17 caracteres, contendo pelo menos uma letra maiúscula, uma minúscula e um caractere especial")
	String senha
) {}
