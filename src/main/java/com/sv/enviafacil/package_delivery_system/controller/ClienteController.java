package com.sv.enviafacil.package_delivery_system.controller;

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

import com.sv.enviafacil.package_delivery_system.dto.request.ClienteCreateRequest;
import com.sv.enviafacil.package_delivery_system.dto.request.ClienteUpdateRequest;
import com.sv.enviafacil.package_delivery_system.service.ClientesServiceImpl;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
	@Autowired
	ClientesServiceImpl service;

	@PostMapping
	public ResponseEntity<?> crearCliente(@Valid @RequestBody ClienteCreateRequest cliente) {
		service.crearCliente(cliente);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	@PatchMapping
	public ResponseEntity<?> modificarCliente(@Valid @RequestBody ClienteUpdateRequest cliente) {
		service.modificarCliente(cliente);
		return ResponseEntity.status(HttpStatus.OK).body(cliente);
	}
	@DeleteMapping("/{id}")
	public ResponseEntity<?> eliminarCliente(@PathVariable int id){
		service.eliminarCliente(id);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
}
