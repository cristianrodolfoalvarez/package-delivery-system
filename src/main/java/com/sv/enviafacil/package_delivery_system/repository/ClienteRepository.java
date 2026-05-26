package com.sv.enviafacil.package_delivery_system.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.sv.enviafacil.package_delivery_system.model.Cliente;

@Repository
public class ClienteRepository {
	private List<Cliente> clientes = new ArrayList<Cliente>();

	public Optional<Cliente> buscarPorId(int id) {
		Optional<Cliente> cliente = this.clientes.stream().filter(c -> c.getId() == id).findFirst();
		return cliente;// puede retornar un optional vacio.
	}

	public boolean guardarCliente(Cliente cliente) {
		System.out.println(cliente.toString());
		return this.clientes.add(cliente);
	}

	public boolean eliminarCliente(int id) {
		this.buscarPorId(id);
		Optional<Cliente> cliente = this.clientes.stream().filter(c -> c.getId() == id).findFirst();
		if (cliente.isEmpty())
			return false;
		cliente.get().setActivo(false);
		return true;
	}
	/*
	 * public void guardarUsuario(Usuario usuario) { int indice =
	 * this.buscarPorId(usuario.getId()); if (this.buscarPorId(usuario.getId()) ==
	 * -1) { this.usuarios.add(usuario); System.out.println("es nuevo"); } else {
	 * System.out.println("es existente"); this.usuarios.set(indice, usuario); }
	 * System.out.println("total usuarios:"+usuarios.size());
	 * 
	 * }
	 */
}
