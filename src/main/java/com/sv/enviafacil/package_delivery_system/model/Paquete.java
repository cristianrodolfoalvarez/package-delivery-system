package com.sv.enviafacil.package_delivery_system.model;

import java.math.BigDecimal;

import com.sv.enviafacil.package_delivery_system.model.enums.EstadoPaquete;

public class Paquete {

    private int id;
    private EstadoPaquete estado;
    private int peso;
    private String descripcion;
    private InformacionDeEnvio envio;
    private int cantidad;
    private BigDecimal precioUnit;
    private Itinerario itinerario;
    private Cliente remitente;
    private Cliente destinatario;
    private static int ultimoId = 0;

    /**
     * Constructor para crear un nuevo paquete
     * @param estado Estado inicial del paquete
     * @param peso Peso en kg
     * @param descripcion Descripción del contenido
     */
    public Paquete(EstadoPaquete estado, int peso, String descripcion, BigDecimal precioUnit, int cantidad) {
        super();
        this.id = Paquete.generarNuevoUltimoId();
        this.estado = estado;
        this.peso = peso;
        this.descripcion = descripcion;
        this.precioUnit = precioUnit;
        this.cantidad = cantidad;
    }

    /**
     * Constructor completo
     */
    public Paquete(EstadoPaquete estado, int peso, String descripcion, 
                   Cliente remitente, Cliente destinatario, BigDecimal precioUnit, int cantidad) {
        this.id = Paquete.generarNuevoUltimoId();
        this.estado = estado;
        this.peso = peso;
        this.descripcion = descripcion;
        this.remitente = remitente;
        this.destinatario = destinatario;
        this.precioUnit = precioUnit;
        this.cantidad = cantidad;

    }

    private static int generarNuevoUltimoId() {
        return Paquete.ultimoId <= 0 ? 1 : ++ultimoId;
    }

    // Getters y Setters
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

    public Cliente getRemitente() {
        return remitente;
    }

    public void setRemitente(Cliente remitente) {
        this.remitente = remitente;
    }

    public Cliente getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(Cliente destinatario) {
        this.destinatario = destinatario;
    }

    public static int getUltimoId() {
        return ultimoId;
    }

    public static void setUltimoId(int ultimoId) {
        Paquete.ultimoId = ultimoId;
    }
    
    public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public BigDecimal getPrecioUnit() {
		return precioUnit;
	}

	public void setPrecioUnit(BigDecimal precioUnit) {
		this.precioUnit = precioUnit;
	}

	@Override
    public String toString() {
        return "Paquete{" +
                "id=" + id +
                ", estado=" + estado +
                ", peso=" + peso +
                ", descripcion='" + descripcion + '\'' +
                ", envio=" + (envio != null ? envio.toString() : "null") +
                ", itinerario=" + (itinerario != null ? itinerario.toString() : "null") +
                ", remitente=" + (remitente != null ? remitente.getNombres() + " " + remitente.getApellidos() : "null") +
                ", destinatario=" + (destinatario != null ? destinatario.getNombres() + " " + destinatario.getApellidos() : "null") +
                '}';
    }
}
