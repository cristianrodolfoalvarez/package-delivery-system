package com.sv.enviafacil.package_delivery_system.model;

public class Itinerario {
	private int id;
	private Sucursal sucursalOrigen;
	private Sucursal  sucursalDestino;
	private int volumendisponible;
	private boolean itinerarioActivo;
	private static int ultimoId = 0;
	
	public Itinerario() {
		this.itinerarioActivo = true;
	}
	
	/**
	 * @param id
	 * @param sucursalOrigen
	 * @param sucursalDestino
	 * @param volumendisponible
	 * @param itinerarioActivo
	 */
	public Itinerario( Sucursal sucursalOrigen, Sucursal sucursalDestino, int volumendisponible,
			boolean itinerarioActivo) {
		super();
		this.id = Itinerario.generarNuevoUltimoId();
		this.sucursalOrigen = sucursalOrigen;
		this.sucursalDestino = sucursalDestino;
		this.volumendisponible = volumendisponible;
		this.itinerarioActivo = itinerarioActivo;
	}
	private static int generarNuevoUltimoId() {
		return Itinerario.ultimoId <= 0? 1 : ++ultimoId; 
	}

	public Sucursal getSucursalOrigen() {
		return sucursalOrigen;
	}
	public void setSucursalOrigen(Sucursal sucursalOrigen) {
		this.sucursalOrigen = sucursalOrigen;
	}
	public Sucursal getSucursalDestino() {
		return sucursalDestino;
	}
	public void setSucursalDestino(Sucursal sucursalDestino) {
		this.sucursalDestino = sucursalDestino;
	}
	public int getVolumendisponible() {
		return volumendisponible;
	}
	public void setVolumendisponible(int volumendisponible) {
		this.volumendisponible = volumendisponible;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public boolean isItinerarioActivo() {
		return itinerarioActivo;
	}
	public void setItinerarioActivo(boolean itinerarioActivo) {
		this.itinerarioActivo = itinerarioActivo;
	}

	@Override
	public String toString() {
		return "Itinerario [id=" + id + ", sucursalOrigen=" + sucursalOrigen + ", sucursalDestino=" + sucursalDestino
				+ ", volumendisponible=" + volumendisponible + ", itinerarioActivo=" + itinerarioActivo + "]";
	}
	
}
