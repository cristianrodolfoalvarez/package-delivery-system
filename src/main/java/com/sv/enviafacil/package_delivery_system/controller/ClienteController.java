// controller/ClienteController.java
package com.sv.enviafacil.package_delivery_system.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sv.enviafacil.package_delivery_system.dto.request.ClienteCreateRequest;
import com.sv.enviafacil.package_delivery_system.model.Cliente;
import com.sv.enviafacil.package_delivery_system.repository.ClienteRepository;
import com.sv.enviafacil.package_delivery_system.utils.ExcepcionPersonalizada;

import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

 // controller/ClienteController.java - Metodo completo
    @PostMapping
    public ResponseEntity<?> crearCliente(@Valid @RequestBody ClienteCreateRequest request) {
        System.out.println("=== POST /clientes ===");
        System.out.println("Nombres: " + request.nombres());
        System.out.println("Apellidos: " + request.apellidos());
        System.out.println("Correo: " + request.correo());
        System.out.println("Telefono: " + request.telefono());
        System.out.println("DUI: " + request.dui());
        
        // Validar duplicados
        if (clienteRepository.buscarPorDUI(request.dui()).isPresent()) {
            Map<String, String> error = new HashMap<>();
            error.put("mensaje", "El DUI ya esta registrado");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
        }
        if (clienteRepository.buscarPorTelefono(request.telefono()).isPresent()) {
            Map<String, String> error = new HashMap<>();
            error.put("mensaje", "El telefono ya esta registrado");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
        }
        if (clienteRepository.buscarPorCorreo(request.correo()).isPresent()) {
            Map<String, String> error = new HashMap<>();
            error.put("mensaje", "El correo electronico ya esta registrado");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
        }

        // Crear cliente con activo = true siempre
        Cliente cliente = new Cliente(
            request.correo(),
            request.nombres(),
            request.apellidos(),
            request.telefono(),
            request.dui(),
            true  // Forzar activo = true
        );
        
        
        
        clienteRepository.guardarCliente(cliente);
        System.out.println("Cliente creado con ID: " + cliente.getId());
        
        return ResponseEntity.status(HttpStatus.CREATED).body(cliente);
    }
    @GetMapping("/todos")
    public ResponseEntity<List<Cliente>> obtenerTodos() {
        System.out.println("=== GET /clientes/todos ===");
        List<Cliente> clientes = clienteRepository.obtenerTodos();
        System.out.println("Total de clientes enviados: " + clientes.size());
        return ResponseEntity.ok(clientes);
    }
}