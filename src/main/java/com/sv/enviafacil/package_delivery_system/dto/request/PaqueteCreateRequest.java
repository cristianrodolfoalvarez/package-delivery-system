package com.sv.enviafacil.package_delivery_system.dto.request;

//import java.math.BigDecimal;

import jakarta.validation.constraints.Min;
//import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
//PENDIENTE AGREGAR LOS DATOS DEL CLIENTE REMITENTE Y DESTINATARIO.
public record PaqueteCreateRequest(/*@NotBlank String estado,*/ @NotNull @Min(value = 1) int peso,
		/* Opcional */ String descripcion, @NotNull @Min(value = 1) int sucursalOrigen,
		@NotNull @Min(value = 1) int sucursalDestino) {
}
//@NotBlank String remitente,@NotBlank  String destinatario