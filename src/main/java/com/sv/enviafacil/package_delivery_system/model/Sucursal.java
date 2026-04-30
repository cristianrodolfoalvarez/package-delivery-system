package com.sv.enviafacil.package_delivery_system.model;

public class Sucursal {
	private final int id;
	private String nombre;
	private String direccion;
	private String telefono;
	private static int ultimoId = 0;
	/**
	 * @param nombre
	 * @param direccion
	 * @param telefono
	 */
	public Sucursal(String nombre, String direccion, String telefono) {
		super();
		//En este momento este campo id tiene una solucion temporal.
		this.id = Sucursal.generarNuevoUltimoId();
		this.nombre = nombre;
		this.direccion = direccion;
		this.telefono = telefono;
	}
	private static int generarNuevoUltimoId() {
		ultimoId += 1;
		return ultimoId;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public int getId() {
		return id;
	}
	@Override
	public String toString() {
		return "id: "+this.id+"\tname :"+this.nombre+"\tDireccion: "+this.direccion+"\tTelefono: "+this.telefono;
	}
}
