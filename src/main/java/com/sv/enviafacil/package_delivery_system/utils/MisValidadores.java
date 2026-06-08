package com.sv.enviafacil.package_delivery_system.utils;
//Crear un metodo para validar numeros de telefono (nacionales)
public class MisValidadores {
	//Validadores para clases record
	public static final String SOLO_LETRAS = "^[a-zA-Z]+$";
	public static final String SOLO_TELEFONO_LOCAL = "^[2567][0-9]{7}$";//"^[0-9]{8}+$";
	public static final String SOLO_DUI_VALIDO = "^[0-9]{9}$";
	
	
	public static boolean esDuiValido(String dui) {
		if(dui == null || dui.length()!= 9)return false;
		if(dui.chars().allMatch(digito -> Character.isDigit(digito)))return true;
		return false;
	}
	/*public static boolean esTelefonoLocalValido(String telefono) {
		if(telefono == null || telefono.length()!= 8)return false;
		if(telefono.chars().allMatch(digito -> Character.isDigit(digito)))return true;
		return false;
	}//return string.matches(regex);*/
	public static boolean esTelefonoLocalValido(String telefono) {
		if(telefono == null || telefono.length()!= 8)return false;
		return telefono.matches(SOLO_TELEFONO_LOCAL);
	}
	
	/**
	 * @param cadena es el término ingresado, y puede ser un teléfono o un DUI.
	 * @return 1 si es un DUI, 2 si es un teléfono local o -1 en cualquier otro caso (cualquier cadena no válida).
	 */
	public static int esDUIoTelefono(String cadena) {
		if(MisValidadores.esDuiValido(cadena))return 1;//es dui.
		if(MisValidadores.esTelefonoLocalValido(cadena))return 2;//es telefono local.
		return -1;
	}
}
