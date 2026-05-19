package com.sv.enviafacil.package_delivery_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
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

import jakarta.validation.Valid;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
	@Autowired
	UsuarioService service;

	@PostMapping
	public void crearUsuario(@Valid @RequestBody UsuarioCreateRequest usuario) {
		service.crearUsuario(usuario);
	}
	
	@PatchMapping
	public void modificarUsuario(@Valid @RequestBody UsuarioUpdateRequest usuario){
		service.modificarUsuario(usuario);
	}
	
	@DeleteMapping("/{id}")
	public void eliminarUsuario(@PathVariable String id) {
		
	}
}
