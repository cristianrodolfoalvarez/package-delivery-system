package com.sv.enviafacil.package_delivery_system.model;

import java.time.LocalDate;
//import java.time.format.DateTimeFormatter;

import com.sv.enviafacil.package_delivery_system.utils.IdGenerator;

/*Pendiente desarrollar esta clase, saber cuales son las mejores formas de guardar dates & times.
 * El tipo de objeto, el formato y el numero de caracteres para guardar el cod. de seguimiento, o si guardar un solo string.
 * ESTA CLASE ESTA PENDIENTE DE IMPLEMENTAR.PD:Cristian.
 * */
public class InformacionDeEnvio {
	
	  private int id;
	  private LocalDate fechaRecepcion;
	  private String codSeguimiento;
	  private static IdGenerator idGenerator = new IdGenerator();
	  /**
 	  * @param id
 	  * @param fechaRecepcion
 	  * @param codSeguimiento
 	  */
	  public InformacionDeEnvio( String codSeguimiento) {
		super();
		this.id = idGenerator.generarNuevoUltimoId();
		this.fechaRecepcion = LocalDate.now();
		this.codSeguimiento = codSeguimiento;
	  }
	  public int getId() {
		  return id;
	  }
	  public LocalDate getFechaRecepcion() {
		  return fechaRecepcion;
	  }
	  public String getCodSeguimiento() {
		  return codSeguimiento;
	  }
	  @Override
	  public String toString() {
		return "InformacionDeEnvio [id=" + id + ", fechaRecepcion=" + fechaRecepcion + ", codSeguimiento="
				+ codSeguimiento + "]";
	  }
	  
}
