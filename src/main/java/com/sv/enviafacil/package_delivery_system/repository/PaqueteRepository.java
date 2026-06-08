package com.sv.enviafacil.package_delivery_system.repository;

import java.util.ArrayList;

import org.springframework.stereotype.Repository;
import com.sv.enviafacil.package_delivery_system.model.Paquete;
import java.util.List;
@Repository
public class PaqueteRepository {
	private List<Paquete> paquetes = new ArrayList<Paquete>();
	public boolean guardarPaquete(Paquete paquete) {
		//System.out.println(paquete.toString());
		return this.paquetes.add(paquete);
	}
	
	public List<Paquete> verPaquetesIngresados() {
		return this.paquetes;
	}
	public int verTotalPaquetes() {
		return this.paquetes.size();
	}
	
}
