package com.sv.enviafacil.package_delivery_system.service;

import com.sv.enviafacil.package_delivery_system.dto.request.ClienteCreateRequest;
import com.sv.enviafacil.package_delivery_system.dto.request.ClienteUpdateRequest;
import com.sv.enviafacil.package_delivery_system.model.Cliente;

public interface ClientesService {
	Cliente buscarClientePorNombre(String nombres, String apellidos);
	Cliente buscarClientePorMail(String correo);
	/*void crearCliente(String correo, String nombres, String apellidos, String telefono, String direccion);*/
	void crearCliente(ClienteCreateRequest nuevoCliente);
	void modificarCliente(ClienteUpdateRequest nuevoCliente);
	void eliminarCliente(int id);

}
