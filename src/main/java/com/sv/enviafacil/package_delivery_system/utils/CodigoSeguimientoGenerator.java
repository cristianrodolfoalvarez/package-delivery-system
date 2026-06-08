package com.sv.enviafacil.package_delivery_system.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CodigoSeguimientoGenerator {
	 private static int secuencia = 1;
	    
	    public static String generarCodigoDeSeguimiento() {
	        String fecha = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
	        return String.format("ENV-%s-%04d", fecha, secuencia++);
	    }
}
