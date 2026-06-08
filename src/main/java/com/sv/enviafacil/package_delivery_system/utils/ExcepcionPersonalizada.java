package com.sv.enviafacil.package_delivery_system.utils;

public class ExcepcionPersonalizada extends RuntimeException{
	private static final long serialVersionUID = 1L;
	private final String campoInvalido;
	public ExcepcionPersonalizada(String campoInvalido, String mensajeAyuda) {
		super(mensajeAyuda);
		this.campoInvalido = campoInvalido;
	}
	public ExcepcionPersonalizada() {
		super("Un error se presentó");
		this.campoInvalido = "error";
	}
	public String getCampoInvalido() {
		return campoInvalido;
	}
}
