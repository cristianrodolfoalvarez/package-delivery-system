// RolController.java
package com.sv.enviafacil.package_delivery_system.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.sv.enviafacil.package_delivery_system.dto.response.RolResponse;
import com.sv.enviafacil.package_delivery_system.service.RolService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/roles")
public class RolController {
    
    @Autowired
    private RolService rolService;
    
    @GetMapping("/disponibles")
    public ResponseEntity<List<RolResponse>> obtenerRolesDisponibles() {
        System.out.println("=== GET /roles/disponibles ===");
        List<RolResponse> roles = rolService.obtenerRolesDisponibles();
        System.out.println("Roles disponibles: " + roles.size());
        return ResponseEntity.ok(roles);
    }
}