package com.sv.enviafacil.package_delivery_system.utils;
//Crear un metodo para validar numeros de telefono (nacionales)
public class MisValidadores {
	//Validadores para clases record
	public static final String SOLO_LETRAS = "^[a-zA-Z]+$";
	public static final String SOLO_TELEFONO_LOCAL = "^[0-9]{8}+$";
	
	
	public static boolean esDuiValido(String dui) {
		if(dui == null || dui.length()!= 9)return false;
		if(dui.chars().allMatch(digito -> Character.isDigit(digito)))return true;
		return false;
	}
	public static boolean esTelefonoLocalValido(String telefono) {
		if(telefono == null || telefono.length()!= 8)return false;
		if(telefono.chars().allMatch(digito -> Character.isDigit(digito)))return true;
		return false;
	}
}
