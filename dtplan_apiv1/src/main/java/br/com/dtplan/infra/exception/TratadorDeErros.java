package br.com.dtplan.infra.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice
public class TratadorDeErros {
	//item não encontrado no banco
	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity TratarErro404() {
		return ResponseEntity.notFound().build();
	}
		
	//Argumentos inválidos 
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity TratarErro400(MethodArgumentNotValidException ex) {
		var erros = ex.getFieldErrors();
		return ResponseEntity.badRequest().body(erros.stream().map(DadosErroValidacao::new).toList());
	}
		
	private record DadosErroValidacao(String campo, String mensagem) {
		public DadosErroValidacao(FieldError erro) {
			this(erro.getField(), erro.getDefaultMessage());
	    }
	}
}
