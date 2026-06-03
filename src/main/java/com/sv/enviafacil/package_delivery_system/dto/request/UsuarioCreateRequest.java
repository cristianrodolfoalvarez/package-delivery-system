package com.sv.enviafacil.package_delivery_system.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UsuarioCreateRequest(
		@Email String correo,
		@NotBlank String rol,
		@NotBlank(message = "El nombre de usuario es obligatorio") @Pattern(regexp = "^[a-zA-Z0-9]{6,12}$", message = "No se permiten caracteres especiales") String nombre,
		@NotBlank @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{6,24}$",
	            message = "Al menos cuatro caracteres, una mayuscula, un caracter especial y un digito.") String contrasena) {

}
