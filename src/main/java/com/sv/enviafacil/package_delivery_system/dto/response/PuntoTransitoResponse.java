package com.sv.enviafacil.package_delivery_system.dto.response;

import java.text.SimpleDateFormat;
import java.util.Date;

public record PuntoTransitoResponse(
    String fecha,
    String sucursal,
    String estado
) {
    public static PuntoTransitoResponse fromModel(Date fecha, String sucursalNombre, String estado) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        return new PuntoTransitoResponse(
            sdf.format(fecha),
            sucursalNombre,
            estado
        );
    }
}