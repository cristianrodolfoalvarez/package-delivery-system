package com.sv.enviafacil.package_delivery_system.model;

import java.util.Date;

public class PuntoDeTransito {
	private Date fechaRecepcion;//Aun pendiente si es lo mas conveniente guardarlo en formato date.
	private Sucursal sucursalRecepcionTemporal;
	
	/**
	 * @param fechaRecepcion
	 * @param sucursalRecepcionTemporal
	 */
	public PuntoDeTransito(Date fechaRecepcion, Sucursal sucursalRecepcionTemporal) {
		super();
		this.fechaRecepcion = fechaRecepcion;
		this.sucursalRecepcionTemporal = sucursalRecepcionTemporal;
	}
	public Date getFechaRecepcion() {
		return fechaRecepcion;
	}
	public void setFechaRecepcion(Date fechaRecepcion) {
		this.fechaRecepcion = fechaRecepcion;
	}
	public Sucursal getSucursalRecepcionTemporal() {
		return sucursalRecepcionTemporal;
	}
	public void setSucursalRecepcionTemporal(Sucursal sucursalRecepcionTemporal) {
		this.sucursalRecepcionTemporal = sucursalRecepcionTemporal;
	}
}
