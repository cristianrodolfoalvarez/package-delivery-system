package com.sv.enviafacil.package_delivery_system.service;

import org.springframework.stereotype.Service;

import com.sv.enviafacil.package_delivery_system.dto.request.SucursalCreateRequest;
import com.sv.enviafacil.package_delivery_system.model.Sucursal;
import com.sv.enviafacil.package_delivery_system.repository.SucursalRepository;

@Service
public class SucursalService {
	private SucursalRepository sucursalRepository;

	public SucursalService(SucursalRepository sucursalRepository) {
		this.sucursalRepository = sucursalRepository;
	}

	
	// Despues estas entidades deben devolver un ResponseEntity <?>
	public void crearSucursal(SucursalCreateRequest nuevaSucursal) {
		// Pend. Validar que el nombre no exista, direccion duplicada, etc.
		Sucursal sucursal = new Sucursal(nuevaSucursal.nombre(), nuevaSucursal.direccion(), nuevaSucursal.telefono());
		this.sucursalRepository.guardarSucursal(sucursal);
		this.sucursalRepository.listarSucursales();

	}

	
	public Sucursal buscarSucursalPorId(int id) {
		return this.sucursalRepository.buscarSucursal(id).orElseThrow();
	}

	
	public void eliminarSucursal(int id) {
		this.sucursalRepository.eliminarSucursal(id);
	}

	/*
	 * public ResponseEntity<?> obtenerSucursalesExistentes(){
	 * 
	 * }
	 */

}
