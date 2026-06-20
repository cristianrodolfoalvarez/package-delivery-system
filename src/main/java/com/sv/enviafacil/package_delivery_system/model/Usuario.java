package com.sv.enviafacil.package_delivery_system.model;

import com.sv.enviafacil.package_delivery_system.model.enums.RolUsuario;
import com.sv.enviafacil.package_delivery_system.utils.IdGenerator;

public class Usuario {
	private final int id;
	private String mail;
	private String nombres;
	private String apellidos;
	private RolUsuario rol;
	private String nombreUsuario;
	private String contrasena;//Deberá almacenarse el hash de la contraseña, no la contraseña en plano en la db.
	private static IdGenerator idGenerator = new IdGenerator();//Clase provisional generadora de ids porque aun no se ha conectado con una db.
	/**
	 * @param mail
	 * @param nombreUsuario
	 * @param contrasena
	 * @param jwtToken
	 */
	public Usuario(String mail, String nombres, String apellidos, RolUsuario rol, String nombreUsuario, String contrasena) {
		super();
		this.id = idGenerator.generarNuevoUltimoId();
		this.mail = mail;
		this.nombres = nombres;
		this.apellidos = apellidos;
		this.rol = rol;
		this.nombreUsuario = nombreUsuario;
		this.contrasena = contrasena;
	}
	
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
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

	public static IdGenerator getIdGenerator() {
		return idGenerator;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}
	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}
	public String getContrasena() {
		return contrasena;
	}
	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	public int getId() {
		return id;
	}

	public RolUsuario getRol() {
		return rol;
	}

	public void setRol(RolUsuario rol) {
		this.rol = rol;
	}
	
}
