package com.sv.enviafacil.package_delivery_system.dto.response;

public record InicioSesionResponse(boolean esValido, String mensaje, String usuario, String rol, Integer id) {

}
