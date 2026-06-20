package com.sv.enviafacil.package_delivery_system.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.sv.enviafacil.package_delivery_system.model.Usuario;
import com.sv.enviafacil.package_delivery_system.model.enums.RolUsuario;
import com.sv.enviafacil.package_delivery_system.utils.ContrasenaUtil;

@Repository
public class UsuarioRepository {
	private List<Usuario> usuarios = new ArrayList<Usuario>();
	private ContrasenaUtil contrasenaUtil = new ContrasenaUtil();
	   public UsuarioRepository() {
	        inicializarUsuarios();
	    }

	private void inicializarUsuarios() {
	        // Administrador - contraseña: admin123
	        Usuario admin = new Usuario(
	        		"admin@enviafacil.com",
	        		"Administrador",
	        		"Primario",
	        		RolUsuario.ADMINISTRADOR_SISTEMA,
	            "admin",
	            contrasenaUtil.encriptar("admin123")
	        );
	        usuarios.add(admin);

	        // Gerente - contraseña: gerente123
	        Usuario gerente = new Usuario(
	        		"carlos@enviafacil.com",
	        		"Carlos",
	        		"Gomez",
	        		RolUsuario.GERENTE_SUCURSAL,
	            "gerente",
	            contrasenaUtil.encriptar("gerente123")
	        );
	        usuarios.add(gerente);

	        // Empleado - contraseña: empleado123
	        Usuario empleado = new Usuario(
	        		"maria@enviafacil.com",
	            "Maria",
	            "Lopez",
	            RolUsuario.EMPLEADO,
	            "empleado",
	            contrasenaUtil.encriptar("empleado123")
	        );
	        usuarios.add(empleado);

	        System.out.println("Usuarios inicializados:");
	        usuarios.forEach(u -> System.out.println("  - " + u.getNombreUsuario() + " | Rol: " + u.getRol()));
	    }

	public void guardarUsuario(Usuario usuario) {
		int indice = this.buscarPorId(usuario.getId());
		if (this.buscarPorId(usuario.getId()) == -1) {
			this.usuarios.add(usuario);
			System.out.println("es nuevo");
		} else {
			System.out.println("es existente");
			this.usuarios.set(indice, usuario);
		}
		System.out.println("total usuarios:" + usuarios.size());

	}

	public Usuario buscarPorUsuario(String usuario) {
		return usuarios.stream().filter(u -> u.getNombreUsuario().equalsIgnoreCase(usuario)).findFirst().orElse(null);
	}

	public int buscarPorId(int id) {
		Optional<Usuario> optional = this.usuarios.stream().filter(u -> u.getId() == id).findFirst();
		return optional.isEmpty() ? -1 : this.usuarios.indexOf(optional.get())/* optional.get().getId() */;
	}

	public Optional<Usuario> obtenerUsuario(int id) {
		Optional<Usuario> optional = this.usuarios.stream().filter(u -> u.getId() == id).findFirst();
		return optional;
	}

	public void listarUsuarios() {
		this.usuarios.forEach(s -> System.out.println("ID:" + s.getId() + "\tNombre:" + s.getNombreUsuario()
				+ "\tcontrasena:" + s.getContrasena() + "\tcorreo: " + s.getMail() + "\tRol:" + s.getRol()));
	}

	// Se debe validar el mail, que no exista tambien.
	public boolean verificarCorreo(String correo) {
		return this.usuarios.stream().anyMatch(m -> m.getMail().equals(correo));
	}

	public boolean verificarNombreUsuario(String nombreUsuario) {
		return this.usuarios.stream().anyMatch(m -> m.getNombreUsuario().equals(nombreUsuario));
	}
}