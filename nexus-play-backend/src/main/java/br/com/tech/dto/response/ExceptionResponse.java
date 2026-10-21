package br.com.tech.dto.response;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ExceptionResponse (
	@JsonProperty(value = "erro")
	Object objErro,
	
	int status,
	
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	LocalDateTime dataHora
) {}