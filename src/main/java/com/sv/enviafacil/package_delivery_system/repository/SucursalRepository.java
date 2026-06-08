package com.sv.enviafacil.package_delivery_system.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.sv.enviafacil.package_delivery_system.model.Sucursal;

@Repository
public class SucursalRepository {
	private List<Sucursal> sucursales = new ArrayList<Sucursal>();
	public SucursalRepository() {
		this.sucursales.add(new Sucursal("Sucursal #1", "Colonia San Benito", "22332222"));
		this.sucursales.add(new Sucursal("Sucursal #2", "Plaza Mundo Apopa", "22223343"));
		this.sucursales.add(new Sucursal("Sucursal #3", "Plaza Mundo Soyapango", "22223233"));
		this.sucursales.add(new Sucursal("Sucursal #4", "Metrocentro San Salvador", "22227333"));
	}

	public void guardarSucursal(Sucursal nuevaSucursal) {
		// if(nuevaSucursal.getId()== null)
		int idSucursal = buscarPorId(nuevaSucursal.getId());
		System.out.println("el id es: " + idSucursal);
		if (idSucursal == -1) {
			System.out.println("nuevo");
			this.sucursales.add(nuevaSucursal);
		} else {
			System.out.println("existente");
			this.sucursales.set(idSucursal, nuevaSucursal);
		}
		System.out.println("# elementos: " + sucursales.size());
		for (Sucursal sucursal : sucursales) {
			System.out.println(sucursal.toString());
		}
	}

	public void listarSucursales() {
		this.sucursales.forEach(s -> System.out.println(s.toString()));
	}
	
	public int obtenerTotalSucursales() {
		return this.sucursales.size();
	}
	
	public List<Sucursal> obtenerTodas() {
		return new ArrayList<>(sucursales);//Enviamos una copia.
	}

	public HashMap<Integer, String>obtenerSucursalesDisponibles() {
		HashMap<Integer, String> sucursales = new HashMap<Integer, String>();
		this.sucursales.forEach(s -> sucursales.put(s.getId(), s.getNombre()));
		return sucursales;//Enviamos una copia.
	}
	
	public Optional<Sucursal> buscarSucursal(int id) {
		//final int posicion = this.buscarPorId(id) != -1? 1:0 ;
		//if(this.buscarPorId(id)!=-1)return Optional.of(this.sucursales.get(id));
		return this.sucursales.stream().filter(s -> s.getId() == id).findFirst();
		
	}
	public void eliminarSucursal(int id) {//Pendiente saber si el borrado sera de la db o solo borrado logico.
		int idExiste = buscarPorId(id);
		System.out.println("entra y el id existente es: "+idExiste);
		this.sucursales.remove(idExiste);
	}

	private int buscarPorId(int id) {
		final int x = this.sucursales.size();
		for (int i = 0; i < x; i++) {
			if (this.sucursales.get(i).getId() == id)
				return i;
		}
		return -1;
	}
}
