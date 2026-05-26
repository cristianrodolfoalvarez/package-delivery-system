package com.sv.enviafacil.package_delivery_system.controller;

//import java.util.Map;
//import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sv.enviafacil.package_delivery_system.dto.request.UsuarioCreateRequest;
import com.sv.enviafacil.package_delivery_system.dto.request.UsuarioUpdateRequest;
import com.sv.enviafacil.package_delivery_system.service.UsuarioService;
//import org.springframework.validation.BindingResult;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
	@Autowired
	UsuarioService service;

	@PostMapping
	public ResponseEntity<?> crearUsuario(@Valid @RequestBody UsuarioCreateRequest usuario) {
		/*if (bindingResult.hasErrors()) {
			System.out.println("\n\nentra al binding.hasErrors()\n\n");
            // A neat way to map all validation errors to a simple response Map
           / *Map<String, String> errors = bindingResult.getFieldErrors().stream()
                    .collect(Collectors.toMap(
                            fieldError -> fieldError.getField(),
                            fieldError -> fieldError.getDefaultMessage()
                    ));* /
            return ResponseEntity.badRequest().body("algo salio mal");
        }*/
		service.crearUsuario(usuario);
		return ResponseEntity.status(HttpStatus.CREATED).body(usuario);
	}
	
	@PatchMapping
	public void modificarUsuario(@Valid @RequestBody UsuarioUpdateRequest usuario){
		service.modificarUsuario(usuario);
	}
	
	@DeleteMapping("/{id}")
	public void eliminarUsuario(@PathVariable String id) {
		
	}
}
