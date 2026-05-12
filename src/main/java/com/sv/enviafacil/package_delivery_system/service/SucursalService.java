package com.sv.enviafacil.package_delivery_system.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.sv.enviafacil.package_delivery_system.dto.request.SucursalCreateRequest;
import com.sv.enviafacil.package_delivery_system.dto.response.SucursalResponse;
import com.sv.enviafacil.package_delivery_system.model.Sucursal;
import com.sv.enviafacil.package_delivery_system.repository.SucursalRepository;

@Service
public class SucursalService{
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
	
	public List<SucursalResponse> listarTodas(){
		//Esta linea es necesario explicar: .()stream es un metodo para las colecciones, abre un flujo de datos, convierte la lista en un flujo procesable
		//lo que permite ahorrarse el for para recorrer cada elemento y trabajar de forma declarativa. Sin el .stream() se tendria que hacer un for tradicional.
		//.map()transforma cada elemento
		//.collect() sirve para recolectar todos los elementos transformados y los guarda en una lista. Escrita por Cristian, para mejor entendimiento.
		return this.sucursalRepository.obtenerTodas().stream().map(sucursal ->this.convertirSucursalAResponse(sucursal)).collect(Collectors.toList());
	}
	
	private SucursalResponse convertirSucursalAResponse(Sucursal sucursal) {
		return new SucursalResponse(sucursal.getId(),sucursal.getNombre(),sucursal.getTelefono(),true);
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
