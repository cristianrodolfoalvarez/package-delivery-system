// PaqueteCreateRequest.java
package com.sv.enviafacil.package_delivery_system.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record PaqueteCreateRequest(
    @NotBlank(message = "El teléfono/DUI del remitente es obligatorio")
    @Pattern(regexp = "^[0-9]{8,9}$", message = "El teléfono/DUI debe tener 8 o 9 dígitos")
    String remitenteDUIOTelefono,
    
    @NotBlank(message = "El teléfono/DUI del destinatario es obligatorio")
    @Pattern(regexp = "^[0-9]{8,9}$", message = "El teléfono/DUI debe tener 8 o 9 dígitos")
    String destinatarioDUIOTelefono,
    
    @NotNull(message = "El peso es obligatorio")
    @Min(value = 1, message = "El peso debe ser mayor a 0")
    int peso,
    
    String descripcion,
    
    @NotNull(message = "La sucursal de origen es obligatoria")
    @Min(value = 1, message = "Seleccione una sucursal válida")
    int sucursalOrigen,
    
    @NotNull(message = "La sucursal de destino es obligatoria")
    @Min(value = 1, message = "Seleccione una sucursal válida")
    int sucursalDestino,
    
    @NotBlank(message = "El estado es obligatorio")
    String estado
) {
}