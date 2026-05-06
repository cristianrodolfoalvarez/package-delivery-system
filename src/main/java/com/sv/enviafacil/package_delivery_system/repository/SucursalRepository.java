package com.sv.enviafacil.package_delivery_system.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.sv.enviafacil.package_delivery_system.model.Sucursal;

@Repository
public class SucursalRepository {
	private List<Sucursal> sucursales = new ArrayList<Sucursal>();

	public void guardarSucursal(Sucursal nuevaSucursal) {
		// if(nuevaSucursal.getId()== null)
		int idSucursal = buscarPorId(nuevaSucursal.getId());
		System.out.println("el di es: " + idSucursal);
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

	public Optional<Sucursal> buscarSucursal(int id) {
		//final int posicion = this.buscarPorId(id) != -1? 1:0 ;
		//if(this.buscarPorId(id)!=-1)return Optional.of(this.sucursales.get(id));
		return this.sucursales.stream().filter(s -> s.getId() == id).findFirst();
		
	}
	public void eliminarSucursal(int id) {
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
