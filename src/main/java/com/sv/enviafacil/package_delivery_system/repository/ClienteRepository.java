// repository/ClienteRepository.java - Codigo completo corregido
package com.sv.enviafacil.package_delivery_system.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.sv.enviafacil.package_delivery_system.model.Cliente;

@Repository
public class ClienteRepository {
    private List<Cliente> clientes = new ArrayList<Cliente>();

    public ClienteRepository() {
        // Cliente de prueba
        Cliente cliente1 = new Cliente("cliente@mail.com", "Juan", "Perez", "77777777", "123456789", true);
        cliente1.setId(1);
        clientes.add(cliente1);
        System.out.println("Cliente de prueba agregado");
    }

    public Optional<Cliente> buscarPorId(int id) {
        return this.clientes.stream()
                .filter(c -> c.getId() == id)
                .findFirst();
    }

    public Optional<Cliente> buscarPorTelefono(String telefono) {
        return this.clientes.stream()
                .filter(c -> c.getTelefono().equals(telefono))
                .findFirst();
    }

    public Optional<Cliente> buscarPorDUI(String dui) {
        return this.clientes.stream()
                .filter(c -> c.getDui().equals(dui))
                .findFirst();
    }

    public Optional<Cliente> buscarPorCorreo(String correo) {
        return this.clientes.stream()
                .filter(c -> c.getCorreo().equalsIgnoreCase(correo))
                .findFirst();
    }

    public List<Cliente> obtenerTodos() {
        return new ArrayList<>(this.clientes);
    }

    public boolean guardarCliente(Cliente cliente) {
        System.out.println("Guardando cliente: " + cliente.toString());
        
        // Forzar activo = true
        cliente.setActivo(true);
        
        if (cliente.getId() == 0) {
            int nuevoId = this.clientes.size() + 1;
            cliente.setId(nuevoId);
            System.out.println("ID asignado: " + nuevoId);
        }
        
        return this.clientes.add(cliente);
    }

    public boolean eliminarCliente(int id) {
        Optional<Cliente> cliente = this.clientes.stream()
                .filter(c -> c.getId() == id)
                .findFirst();
        if (cliente.isEmpty()) {
            System.out.println("Cliente no encontrado para eliminar: " + id);
            return false;
        }
        cliente.get().setActivo(false);
        System.out.println("Cliente desactivado: " + cliente.get().getNombres());
        return true;
    }
}
