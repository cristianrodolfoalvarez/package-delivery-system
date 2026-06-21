package com.sv.enviafacil.package_delivery_system.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ActualizarEstadoRequest(
 @NotBlank(message = "El nuevo estado es obligatorio")
 String nuevoEstado,
 
 @NotNull(message = "La sucursal es obligatoria")
 Integer sucursalId
) {}
