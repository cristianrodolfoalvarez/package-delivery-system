package com.sv.enviafacil.package_delivery_system.service;

import com.sv.enviafacil.package_delivery_system.model.Cliente;

public interface ClientesService {
	Cliente buscarClientePorNombre(String nombres, String apellidos);
	Cliente buscarClientePorMail(String correo);
	void crearCliente(String correo, String nombres, String apellidos, String telefono, String direccion);
}
