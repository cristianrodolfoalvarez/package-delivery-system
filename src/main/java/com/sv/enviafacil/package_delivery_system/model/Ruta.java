package com.sv.enviafacil.package_delivery_system.model;

public class Ruta {
	private Sucursal sucursalOrigen;
	private Sucursal sucursalDestino;
	/**
	 * @param sucursalOrigen
	 * @param sucursalDestino
	 */
	public Ruta(Sucursal sucursalOrigen, Sucursal sucursalDestino) {
		super();
		this.sucursalOrigen = sucursalOrigen;
		this.sucursalDestino = sucursalDestino;
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
}
