package com.sv.enviafacil.package_delivery_system.utils;

public class IdGenerator {
	private int ultimoId = 0;
	public int generarNuevoUltimoId() {
		ultimoId += 1;
		return ultimoId;
	}
}
