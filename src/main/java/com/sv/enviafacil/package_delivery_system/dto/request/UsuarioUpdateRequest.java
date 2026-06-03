package com.sv.enviafacil.package_delivery_system.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record UsuarioUpdateRequest(@NotNull @Positive int id, @Email String correo, String rol,
		@NotBlank(message = "El nombre de usuario es obligatorio") @Pattern(regexp = "^[a-zA-Z0-9]{6,12}$", message = "No se permiten caracteres especiales") String nombre,
		@NotBlank @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{6,24}$", message = "Al menos cuatro caracteres, una mayuscula, un caracter especial y un digito.") String contrasena,
		 @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{6,24}$", message = "Al menos cuatro caracteres, una mayuscula, un caracter especial y un digito.") String nuevaContrasena) {

}