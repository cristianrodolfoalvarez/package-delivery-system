package com.sv.enviafacil.package_delivery_system.model;

import com.sv.enviafacil.package_delivery_system.model.enums.RolUsuario;
import com.sv.enviafacil.package_delivery_system.utils.IdGenerator;

public class Usuario {
	private final int id;
	private String mail;
	private RolUsuario rol;
	private String nombreUsuario;
	private String contrasena;//Deberá almacenarse el hash de la contraseña, no la contraseña en plano en la db.
	private String jwtToken;//Para agregar una capa de seguridad adicional. Pero pendiente de implementar.
	private static IdGenerator idGenerator = new IdGenerator();//Clase provisional generadora de ids porque aun no se ha conectado con una db.
	/**
	 * @param mail
	 * @param nombreUsuario
	 * @param contrasena
	 * @param jwtToken
	 */
	public Usuario(String mail, RolUsuario rol, String nombreUsuario, String contrasena, String jwtToken) {
		super();
		this.id = idGenerator.generarNuevoUltimoId();
		this.mail = mail;
		this.rol = rol;
		this.nombreUsuario = nombreUsuario;
		this.contrasena = contrasena;
		this.jwtToken = jwtToken;
	}
	
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
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
	public String getJwtToken() {
		return jwtToken;
	}
	public void setJwtToken(String jwtToken) {
		this.jwtToken = jwtToken;
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
