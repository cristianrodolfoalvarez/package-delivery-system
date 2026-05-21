package com.sv.enviafacil.package_delivery_system.dto.request;

//import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record SucursalCreateRequest(@NotBlank(message = "El nombre es obligatorio") String nombre,
		@NotBlank String direccion,
		@NotBlank @Pattern(regexp = "\\d{4}-\\d{4}", message = "Telefono no valido") String telefono) {
}
