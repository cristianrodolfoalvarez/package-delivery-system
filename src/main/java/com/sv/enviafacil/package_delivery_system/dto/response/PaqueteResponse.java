// dto/response/PaqueteResponse.java
package com.sv.enviafacil.package_delivery_system.dto.response;

import java.math.BigDecimal;
import java.util.List;

public record PaqueteResponse(
    int id,
    String codigoSeguimiento,
    String remitente,
    String destinatario,
    String estado,
    String sucursalOrigen,
    String sucursalDestino,
    String ubicacionActual,
    List<PuntoTransitoResponse> puntosDeTransito
) {}