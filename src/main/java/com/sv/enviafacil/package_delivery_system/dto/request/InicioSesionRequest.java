package com.sv.enviafacil.package_delivery_system.dto.request;

import jakarta.validation.constraints.NotBlank;

public record InicioSesionRequest(
	    @NotBlank(message = "El usuario es obligatorio")
	    String usuario,
	    
	    @NotBlank(message = "La contraseña es obligatoria")
	    String contrasena
	) {}

