package com.sv.enviafacil.package_delivery_system.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.sv.enviafacil.package_delivery_system.utils.ExcepcionPersonalizada;

@RestControllerAdvice
public class ExcepcionesController {
	   @ExceptionHandler(MethodArgumentNotValidException.class)
	    public ResponseEntity<?> handleValidationErrors(MethodArgumentNotValidException e) {
	        Map<String, String> errores = new HashMap<>();
	        e.getBindingResult().getFieldErrors().forEach(error -> 
	            errores.put(error.getField(), error.getDefaultMessage())
	        );
	        return ResponseEntity.badRequest().body(errores);
	    }
	/*@ResponseStatus(code=HttpStatus.BAD_REQUEST)
	@ExceptionHandler(RuntimeException.class)
	public Map<String, String> manejarRunTimeException(MethodArgumentNotValidException / *RuntimeException* / e){
		Map<String, String> errores = new HashMap<String, String>();
		e.getAllErrors().forEach((error)->{String nombreCampo = ((FieldError)error).getField();
		String mensaje = error.getDefaultMessage();
		errores.put(nombreCampo, mensaje);
		});
		return errores;
	}*/
	@ResponseStatus(code=HttpStatus.BAD_REQUEST)
	 @ExceptionHandler(ExcepcionPersonalizada.class)
	    public ResponseEntity<?> handleEmailDuplicado(ExcepcionPersonalizada e) {
	        Map<String, String> error = Map.of(e.getCampoInvalido(), e.getMessage());
	        return ResponseEntity.badRequest().body(error);
	    }
}
