package com.sv.enviafacil.package_delivery_system.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.sv.enviafacil.package_delivery_system.model.Paquete;

@Repository
public class PaqueteRepository {
    
    private List<Paquete> paquetes = new ArrayList<Paquete>();
    
    public boolean guardarPaquete(Paquete paquete) {
        return this.paquetes.add(paquete);
    }
    
    public List<Paquete> verPaquetesIngresados() {
        return this.paquetes;
    }
    
    public int verTotalPaquetes() {
        return this.paquetes.size();
    }
    
    // Métodos adicionales útiles (opcionales pero recomendados)
    
    public Optional<Paquete> buscarPorId(int id) {
        return this.paquetes.stream()
                .filter(paquete -> paquete.getId() == id)
                .findFirst();
    }
    
    public List<Paquete> buscarPorRemitente(int idRemitente) {
        return this.paquetes.stream()
                .filter(paquete -> paquete.getRemitente() != null && 
                                   paquete.getRemitente().getId() == idRemitente)
                .toList();
    }
    
    public List<Paquete> buscarPorDestinatario(int idDestinatario) {
        return this.paquetes.stream()
                .filter(paquete -> paquete.getDestinatario() != null && 
                                   paquete.getDestinatario().getId() == idDestinatario)
                .toList();
    }
    
    public List<Paquete> buscarPorEstado(String estado) {
        return this.paquetes.stream()
                .filter(paquete -> paquete.getEstado().name().equalsIgnoreCase(estado))
                .toList();
    }
    
    public boolean actualizarEstado(int id, String nuevoEstado) {
        Optional<Paquete> paqueteOpt = buscarPorId(id);
        if (paqueteOpt.isPresent()) {
            Paquete paquete = paqueteOpt.get();
            paquete.setEstado(com.sv.enviafacil.package_delivery_system.model.enums.EstadoPaquete.valueOf(nuevoEstado.toUpperCase()));
            return true;
        }
        return false;
    }
    
    public void limpiarTodosLosPaquetes() {
        this.paquetes.clear();
    }
}