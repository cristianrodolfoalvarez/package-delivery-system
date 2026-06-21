// controller/AuthController.java
package com.sv.enviafacil.package_delivery_system.controller;

import com.sv.enviafacil.package_delivery_system.dto.request.InicioSesionRequest;
import com.sv.enviafacil.package_delivery_system.dto.response.InicioSesionResponse;
import com.sv.enviafacil.package_delivery_system.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<InicioSesionResponse> login(@Valid @RequestBody InicioSesionRequest request) {
        System.out.println("=== POST /auth/login ===");
        InicioSesionResponse response = authService.autenticar(request);
        return ResponseEntity.ok(response);
    }
}
