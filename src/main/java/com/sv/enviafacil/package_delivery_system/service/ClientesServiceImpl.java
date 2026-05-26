package com.sv.enviafacil.package_delivery_system.service;

import org.springframework.stereotype.Service;

//import java.util.Arrays;

import com.sv.enviafacil.package_delivery_system.dto.request.ClienteCreateRequest;
import com.sv.enviafacil.package_delivery_system.dto.request.ClienteUpdateRequest;
import com.sv.enviafacil.package_delivery_system.model.Cliente;
import com.sv.enviafacil.package_delivery_system.repository.ClienteRepository;
import com.sv.enviafacil.package_delivery_system.utils.ExcepcionPersonalizada;
//import com.sv.enviafacil.package_delivery_system.model.enums.RolCliente;
//import com.sv.enviafacil.package_delivery_system.utils.ExcepcionPersonalizada;
import com.sv.enviafacil.package_delivery_system.utils.MisValidadores;

@Service
public class ClientesServiceImpl implements ClientesService {
	private ClienteRepository clienteRepository;
	
	public ClientesServiceImpl(ClienteRepository clienteRepository) {
		this.clienteRepository = clienteRepository;
	}
	@Override
	public Cliente buscarClientePorNombre(String nombres, String apellidos) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Cliente buscarClientePorMail(String correo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void crearCliente(ClienteCreateRequest nuevoCliente) {
		if (!MisValidadores.esDuiValido(nuevoCliente.dui()))
			throw new ExcepcionPersonalizada("dui", "El número de DUI no es un número válido.");
		if(!MisValidadores.esTelefonoLocalValido(nuevoCliente.telefono()))
			throw new ExcepcionPersonalizada("telefono", "El número de telefono no es un número válido.");
		Cliente cliente = new Cliente(nuevoCliente.correo(), nuevoCliente.nombres(), nuevoCliente.apellidos(),
				nuevoCliente.telefono(), nuevoCliente.dui(),nuevoCliente.activo());
		this.clienteRepository.guardarCliente(cliente);
		//return cliente;
	}
	@Override
	public void modificarCliente(ClienteUpdateRequest nuevoCliente) {
		Cliente cliente = clienteRepository.buscarPorId(nuevoCliente.id()).orElse(null);
		if (cliente == null)throw new ExcepcionPersonalizada("id", "El cliente no fue encontrado.");
		else System.out.println("si se encontro el cliente");
		
	}
	@Override
	public void eliminarCliente(int id) {
		
	}
	
	/*
	 * private RolCliente convertirRolCliente(String rol) { try { return
	 * RolCliente.valueOf(rol.toUpperCase()); } catch (IllegalArgumentException e) {
	 * throw new ExcepcionPersonalizada("El rol para el cliente no es uno válido"
	 * ,"Roles válidos: "+Arrays.toString(RolCliente.values())); } }
	 */
}
