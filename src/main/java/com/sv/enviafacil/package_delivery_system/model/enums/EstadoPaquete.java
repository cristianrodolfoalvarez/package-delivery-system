// model/enums/EstadoPaquete.java
package com.sv.enviafacil.package_delivery_system.model.enums;

public enum EstadoPaquete {
    PENDIENTE("Pendiente"),
    EN_RUTA("En Ruta"),
    LLEGO_A_ESCALA("Llego a Escala"),
    DISPONIBLE_PARA_RETIRO("Disponible para Retiro"),
    ENTREGADO("Entregado"),
    ANULADO("Anulado");

    private final String descripcion;

    EstadoPaquete(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public static EstadoPaquete fromString(String texto) {
        if (texto == null) {
            return null;
        }
        for (EstadoPaquete estado : EstadoPaquete.values()) {
            if (estado.name().equalsIgnoreCase(texto.trim())) {
                return estado;
            }
        }
        throw new IllegalArgumentException("Estado no valido: " + texto);
    }

    public static boolean esEstadoValido(String texto) {
        try {
            fromString(texto);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public boolean esEstadoFinal() {
        return this == ENTREGADO || this == ANULADO;
    }

    public boolean permiteCambioA(EstadoPaquete nuevoEstado) {
        if (this == ANULADO || this == ENTREGADO) {
            return false;
        }
        if (this == PENDIENTE) {
            return nuevoEstado == EN_RUTA || nuevoEstado == ANULADO;
        }
        if (this == EN_RUTA) {
            return nuevoEstado == LLEGO_A_ESCALA || nuevoEstado == ANULADO;
        }
        if (this == LLEGO_A_ESCALA) {
            return nuevoEstado == EN_RUTA || nuevoEstado == DISPONIBLE_PARA_RETIRO || nuevoEstado == ANULADO;
        }
        if (this == DISPONIBLE_PARA_RETIRO) {
            return nuevoEstado == ENTREGADO || nuevoEstado == ANULADO;
        }
        return false;
    }

    public String getObservacionPorEstado() {
        switch (this) {
            case PENDIENTE:
                return "Paquete recibido. Pendiente de despacho.";
            case EN_RUTA:
                return "Paquete en transito hacia la sucursal destino.";
            case LLEGO_A_ESCALA:
                return "Paquete llego a escala. Esperando siguiente ruta.";
            case DISPONIBLE_PARA_RETIRO:
                return "Paquete disponible para retiro en sucursal destino.";
            case ENTREGADO:
                return "Paquete entregado al destinatario.";
            case ANULADO:
                return "Paquete anulado. No sera entregado.";
            default:
                return "Paquete en proceso.";
        }
    }
}