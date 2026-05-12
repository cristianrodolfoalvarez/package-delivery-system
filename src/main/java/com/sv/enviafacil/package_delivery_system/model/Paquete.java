package com.sv.enviafacil.package_delivery_system.model;

import com.sv.enviafacil.package_delivery_system.model.enums.EstadoPaquete;

public class Paquete {

	private int id;
 private EstadoPaquete estado;
 private double peso;
 private String direccion;
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
 public double getPeso() {
	return peso;
 }
 public void setPeso(double peso) {
	this.peso = peso;
 }
 public String getDireccion() {
	return direccion;
 }
 public void setDireccion(String direccion) {
	this.direccion = direccion;
 } 
}
