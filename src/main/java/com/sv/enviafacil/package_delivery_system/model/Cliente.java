package com.sv.enviafacil.package_delivery_system.model;

import com.sv.enviafacil.package_delivery_system.utils.IdGenerator;

public class Cliente {
	private final int id;
	private String correo;
	private String nombres;
	private String apellidos;
	private String telefono;
	private String dui;
	private boolean activo;
	private static IdGenerator idGenerator = new IdGenerator();//Clase provisional generadora de ids porque aun no se ha conectado con una db.
	/**
	 * @param correo
	 * @param nombres
	 * @param apellidos
	 * @param telefono
	 * @param dui
	 */
	//se debe agregar un atributo para cambiar de estado cuando se solicite eliminar y cambiar ese attr a false;
	public Cliente(String correo, String nombres, String apellidos, String telefono, String dui, boolean activo) {
		super();
		this.id = idGenerator.generarNuevoUltimoId();
		this.correo = correo;
		this.nombres = nombres;
		this.apellidos = apellidos;
		this.telefono = telefono;
		this.dui = dui;//Posiblemente direccion tambien podria ser una clase independiente, debido a que una direccion se divide en calles, avenidas, pasaje, etc.
		
	}
	public String getCorreo() {
		return correo;
	}
	public void setCorreo(String correo) {
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
	public String getDui() {
		return dui;
	}
	public void setDui(String direccion) {
		this.dui = direccion;
	}
	public int getId() {
		return id;
	}

	public boolean isActivo() {
		return activo;
	}
	public void setActivo(boolean activo) {
		this.activo = activo;
	}
	@Override
	public String toString() {
		return "Cliente [id=" + id + ", correo=" + correo + ", nombres=" + nombres + ", apellidos=" + apellidos
				+ ", telefono=" + telefono + ", dui=" + dui + "]";
	}
	
}
