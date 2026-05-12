package com.sv.enviafacil.package_delivery_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sv.enviafacil.package_delivery_system.dto.request.SucursalCreateRequest;
import com.sv.enviafacil.package_delivery_system.service.SucursalService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/sucursales")
public class SucursalController {
	@Autowired
	SucursalService service;

	@PostMapping
	public void crearSucursal(@Valid @RequestBody SucursalCreateRequest sucursal) {
		// System.out.println(sucursal.toString());
		//service = new SucursalServiceImpl();
		service.crearSucursal(sucursal);
	}
	@GetMapping("/{id}")
	public void buscarSucursalPorId(@PathVariable int id) {
		System.out.println(service.buscarSucursalPorId(id));
	}
	@DeleteMapping("/{id}")
	public void borrarSucursal(@PathVariable int id) {
		service.eliminarSucursal(id);
	}
	@GetMapping
	public ResponseEntity<?> obtenerSucursales() {
		return ResponseEntity.ok(this.service.listarTodas());
	}

}
