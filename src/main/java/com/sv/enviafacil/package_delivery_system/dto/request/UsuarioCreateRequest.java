package com.sv.enviafacil.package_delivery_system.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record UsuarioCreateRequest(
		@NotBlank(message = "Los nombres son obligatorios") @Pattern(regexp = "^[a-zA-Z]{3,25}$", message = "Solo se permiten letras")
		String nombres, 
		@NotBlank(message = "Los apellidos son obligatorios") @Pattern(regexp = "^[a-zA-Z]{3,25}$", message = "Solo se permiten letras")
		String apellidos,
		@NotBlank
		@Email String correo,
	    @NotNull(message = "La sucursal es un campo obligatorio")
	    @Min(value = 1, message = "El ID de sucursal debe ser uno válido")
	    int sucursal,
		@NotBlank String cargo,
		@NotBlank(message = "El nombre de usuario es obligatorio") @Pattern(regexp = "^[a-z][a-z0-9]{2,15}$",
		message = "No se permiten caracteres especiales") String usuario,
		@NotBlank @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{6,24}$",
	            message = "Al menos cuatro caracteres, una mayuscula, un caracter especial y un digito.") 
		String contrasena) {

}
