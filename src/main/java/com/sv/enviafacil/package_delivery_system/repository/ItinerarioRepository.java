package com.sv.enviafacil.package_delivery_system.repository;

//import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.sv.enviafacil.package_delivery_system.model.Itinerario;
import com.sv.enviafacil.package_delivery_system.model.Sucursal;

@Repository
public class ItinerarioRepository {
	// sucursales.obtenerSucursalesDisponibles().forEach((i,s)->System.out.println("key:
	// "+i+"\tValue: "+s));
	private List<Itinerario> itinerarios = new ArrayList<Itinerario>();

	public Optional<Itinerario> verificarItinerarioExistente(Sucursal origen, Sucursal destino) {
		return this.itinerarios.stream().filter(itinerario -> itinerario.getSucursalOrigen().getId() == origen.getId()
				&& itinerario.getSucursalDestino().getId() == destino.getId()).findFirst();
	}

	public Optional<Itinerario> verificarItinerarioExistente(int sucursalOrigen, int sucursalDestino) {
		// if!true=false
		Optional<Itinerario> optional = this.itinerarios.stream()
				.filter(itinerario -> itinerario.isItinerarioActivo()
						&& !(itinerario.getSucursalOrigen().getId() != sucursalOrigen
								|| itinerario.getSucursalDestino().getId() != sucursalDestino))
				.findFirst();
		return optional;
	}

	//
		public boolean crearItinerario(Itinerario itineario) {
		return this.itinerarios.add(itineario);
	}
	/*
	 * this.id = id; this.sucursalOrigen = sucursalOrigen; this.sucursalDestino =
	 * sucursalDestino; this.volumendisponible = volumendisponible;
	 * this.itinerarioActivo = itinerarioActivo;
	 */
	/*
	 * public void crearItinerario(Sucursal) {
	 * 
	 * }
	 */

}
