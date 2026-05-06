package com.sv.enviafacil.package_delivery_system.model;

import com.sv.enviafacil.package_delivery_system.utils.IdGenerator;

public class Cliente {
	private final int id;
	private String correo;
	private String nombres;
	private String apellidos;
	private String telefono;
	private String direccion;
	private static IdGenerator idGenerator;//Clase provisional generadora de ids porque aun no se ha conectado con una db.
	/**
	 * @param correo
	 * @param nombres
	 * @param apellidos
	 * @param telefono
	 * @param direccion
	 */
	public Cliente(String correo, String nombres, String apellidos, String telefono, String direccion) {
		super();
		this.id = idGenerator.generarNuevoUltimoId();
		this.correo = correo;
		this.nombres = nombres;
		this.apellidos = apellidos;
		this.telefono = telefono;
		this.direccion = direccion;//Posiblemente direccion tambien podria ser una clase independiente, debido a que una direccion se divide en calles, avenidas, pasaje, etc.
	}
	public String getcorreo() {
		return correo;
	}
	public void setcorreo(String correo) {
		this.correo = correo;
	}
	public String getNombres() {
		return nombres;
	}
	public void setNombres(String nombres) {
		this.nombres = nombres;
	}
	public String getApellidos() {
		return apellidos;
	}
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public int getId() {
		return id;
	}
	
}
