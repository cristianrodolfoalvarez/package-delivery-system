package com.sv.enviafacil.package_delivery_system.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sv.enviafacil.package_delivery_system.dto.request.PaqueteCreateRequest;
import com.sv.enviafacil.package_delivery_system.dto.response.ClienteResponse;
import com.sv.enviafacil.package_delivery_system.service.PaqueteService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/paquetes")
public class PaqueteController {
	@Autowired
	PaqueteService service;

	@PostMapping
	public ResponseEntity<?> crearPaquete(@Valid @RequestBody PaqueteCreateRequest paquete) {
		service.crearPaquete(paquete);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@GetMapping("/buscar")
	public ResponseEntity<HashMap<Integer, String>> buscarCliente(@RequestParam(required = false) String termino) {
		System.out.println("entra");
		ClienteResponse cliente = service.buscarCliente(termino);
		if (cliente != null) {
			HashMap<Integer, String> clienteTupla = new HashMap<Integer, String>();
			clienteTupla.put(cliente.id(), cliente.nombreCompleto());
			// cliente.id(),cliente.nombreCompleto()
			return ResponseEntity.status(HttpStatus.OK).body(clienteTupla);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}
}
