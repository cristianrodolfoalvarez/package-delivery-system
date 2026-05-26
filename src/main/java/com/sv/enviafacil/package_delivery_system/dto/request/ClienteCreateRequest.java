package com.sv.enviafacil.package_delivery_system.dto.request;

import com.sv.enviafacil.package_delivery_system.utils.MisValidadores;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

//A lo mejor es mas util solicitar el dui que la direccion. Porque siempre se recoge en sucursal, realmente 
//la direccion del cliente no se necesita
public record ClienteCreateRequest(@Email String correo,
		@NotBlank @Size(min = 3) @Pattern(regexp = MisValidadores.SOLO_LETRAS) String nombres,
		@NotBlank @Size(min = 3) @Pattern(regexp = MisValidadores.SOLO_LETRAS) String apellidos,
		@NotBlank @Pattern(regexp = MisValidadores.SOLO_TELEFONO_LOCAL, 
		message = "El número de teléfono deben de ser ocho dígitos, sin espacios ni caracteres especiales.") String telefono, // XXXX-XXXX																																													// (XXX)XXX-XXXX
		@NotBlank String dui,// XXXXXXXX-X
		boolean activo
) {
}
/*
 * private final int id; private String correo; private String nombres; private
 * String apellidos; private String telefono; private String direccion;
 */