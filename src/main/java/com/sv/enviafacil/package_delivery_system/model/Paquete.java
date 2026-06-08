package com.sv.enviafacil.package_delivery_system.model;

//import java.math.BigDecimal;

import com.sv.enviafacil.package_delivery_system.model.enums.EstadoPaquete;

public class Paquete {

	private int id;
	private EstadoPaquete estado;
	private int peso;
	private String descripcion;
	private InformacionDeEnvio envio;
	private Itinerario itinerario;
	private static int ultimoId = 0;
	

	/**
	 * @param estado
	 * @param peso
	 */
	public Paquete(EstadoPaquete estado, int peso, String descripcion) {
		super();
		this.id = Paquete.generarNuevoUltimoId();
		this.estado = estado;
		this.peso = peso;
		this.descripcion = descripcion;
	}
	
	private static int generarNuevoUltimoId() {
		return Paquete.ultimoId <= 0? 1 : ++ultimoId; 
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public EstadoPaquete getEstado() {
		return estado;
	}

	public void setEstado(EstadoPaquete estado) {
		this.estado = estado;
	}

	public int getPeso() {
		return peso;
	}

	public void setPeso(int peso) {
		this.peso = peso;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public InformacionDeEnvio getEnvio() {
		return envio;
	}

	public void setEnvio(InformacionDeEnvio envio) {
		this.envio = envio;
	}

	public Itinerario getItinerario() {
		return itinerario;
	}

	public void setItinerario(Itinerario itinerario) {
		this.itinerario = itinerario;
	}

	@Override
	public String toString() {
		return "Paquete [id=" + id + ", estado=" + estado + ", peso=" + peso + ", descripcion=" + descripcion
				+ ", envio=" + envio.toString() + ", itinerario=" + itinerario.toString() + "]";
	}
	

	

}
