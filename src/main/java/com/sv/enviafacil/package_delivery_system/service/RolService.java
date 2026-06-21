package com.sv.enviafacil.package_delivery_system.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import com.sv.enviafacil.package_delivery_system.dto.response.RolResponse;
import com.sv.enviafacil.package_delivery_system.model.enums.RolUsuario;

@Service
public class RolService {
 
 /**
  * Obtiene todos los roles disponibles
  */
 public List<RolResponse> obtenerRolesDisponibles() {
     List<RolResponse> roles = new ArrayList<>();
     
     for (RolUsuario rol : RolUsuario.values()) {
         String descripcion = obtenerDescripcion(rol);
         roles.add(new RolResponse(rol.name(), descripcion));
     }
     
     return roles;
 }
 
 /**
  * Obtiene la descripción de cada rol
  */
 private String obtenerDescripcion(RolUsuario rol) {
     switch (rol) {
         case EMPLEADO:
             return "Empleado de sucursal";
         case GERENTE_SUCURSAL:
             return "Gerente de sucursal";
         case ADMINISTRADOR_SISTEMA:
             return "Administrador del sistema";
         default:
             return rol.name();
     }
 }
}
