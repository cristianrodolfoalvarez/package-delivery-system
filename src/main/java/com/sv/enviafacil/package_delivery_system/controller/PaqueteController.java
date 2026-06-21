package com.sv.enviafacil.package_delivery_system.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sv.enviafacil.package_delivery_system.dto.request.ActualizarEstadoRequest;
import com.sv.enviafacil.package_delivery_system.dto.request.PaqueteCreateRequest;
import com.sv.enviafacil.package_delivery_system.dto.response.ClienteResponse;
import com.sv.enviafacil.package_delivery_system.dto.response.PaqueteResponse;
import com.sv.enviafacil.package_delivery_system.service.PaqueteService;

import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/paquetes")
public class PaqueteController {
	@Autowired
	PaqueteService service;

	// PaqueteController.java
	@PostMapping
	public ResponseEntity<?> crearPaquete(@Valid @RequestBody PaqueteCreateRequest paquete) {
	    System.out.println("=== POST RECIBIDO EN SPRING ===");
	    System.out.println("URL: /paquetes");
	    System.out.println("Método: POST");
	    System.out.println("Datos recibidos: " + paquete);
	    System.out.println("Remitente: " + paquete.remitenteDUIOTelefono());
	    System.out.println("Destinatario: " + paquete.destinatarioDUIOTelefono());
	    System.out.println("Sucursal Origen: " + paquete.sucursalOrigen());
	    System.out.println("Sucursal Destino: " + paquete.sucursalDestino());
	    System.out.println("Peso: " + paquete.peso());
	    System.out.println("Cantidad paquetes: " + paquete.cantidad());
	    System.out.println("Precio unitario: " + paquete.precio());
	    System.out.println("Descripción: " + paquete.descripcion());
	    System.out.println("Estado: " + paquete.estado());
	    System.out.println("=============================");
	    
	    //service.crearPaquete(paquete);
	    return ResponseEntity.status(HttpStatus.CREATED).body(service.crearPaquete(paquete));
	}

	@GetMapping("/buscar")
	public ResponseEntity<HashMap<Integer, String>> buscarCliente(@RequestParam(required = false) String termino) {
		System.out.println("entra en el buscarCliente");
		ClienteResponse cliente = service.buscarCliente(termino);
		if (cliente != null) {
			HashMap<Integer, String> clienteTupla = new HashMap<Integer, String>();
			clienteTupla.put(cliente.id(), cliente.nombreCompleto());
			// cliente.id(),cliente.nombreCompleto()
			return ResponseEntity.status(HttpStatus.OK).body(clienteTupla);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}
	
    @GetMapping("/todos")
    public ResponseEntity<List<PaqueteResponse>> obtenerTodosLosPaquetes() {
        System.out.println("=== GET /paquetes/todos ===");
        List<PaqueteResponse> paquetes = service.obtenerTodosLosPaquetes();
        return ResponseEntity.ok(paquetes);
    }

    @GetMapping("/buscar/{codigo}")
    public ResponseEntity<PaqueteResponse> buscarPorCodigo(@PathVariable String codigo) {
        System.out.println("=== GET /paquetes/buscar/" + codigo + " ===");
        PaqueteResponse paquete = service.buscarPaquetePorCodigo(codigo);
        if (paquete != null) {
            return ResponseEntity.ok(paquete);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<?> actualizarEstado(
            @PathVariable int id,
            @Valid @RequestBody ActualizarEstadoRequest request) {
        System.out.println("=== PUT /paquetes/" + id + "/estado ===");
        System.out.println("Nuevo estado: " + request.nuevoEstado());
        System.out.println("Sucursal ID: " + request.sucursalId());
        
        boolean actualizado = service.actualizarEstadoPaquete(id, request);
        
        if (actualizado) {
            return ResponseEntity.ok().body(Map.of("mensaje", "Estado actualizado correctamente"));
        }
        return ResponseEntity.notFound().build();
    }
}
