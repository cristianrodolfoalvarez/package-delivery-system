package com.sv.enviafacil.package_delivery_system.service;

import java.util.Arrays;

import org.springframework.stereotype.Service;
//import org.springframework.web.bind.MethodArgumentNotValidException;

import com.sv.enviafacil.package_delivery_system.dto.request.UsuarioCreateRequest;
import com.sv.enviafacil.package_delivery_system.dto.request.UsuarioUpdateRequest;
import com.sv.enviafacil.package_delivery_system.model.Usuario;
import com.sv.enviafacil.package_delivery_system.model.enums.RolUsuario;
import com.sv.enviafacil.package_delivery_system.repository.UsuarioRepository;
import com.sv.enviafacil.package_delivery_system.utils.ExcepcionPersonalizada;

@Service
public class UsuarioService {
	private UsuarioRepository usuarioRepository;

	public UsuarioService(UsuarioRepository usuarioRepository) {
		this.usuarioRepository = usuarioRepository;
	}

	public void crearUsuario(UsuarioCreateRequest nuevoUsuario) {
		Usuario usuario = new Usuario(nuevoUsuario.correo(), convertirRolUsuario(nuevoUsuario.rol()),
				nuevoUsuario.nombre(), nuevoUsuario.contrasena(), "JWTOKEN");
		if (!verificarNoDuplicidadCorreo(nuevoUsuario.correo()))
			throw new ExcepcionPersonalizada("correo", "El correo electrónico ya está en uso.");// System.out.println("correo
																								// electronico y/o
																								// nombre de usuario no
																								// disponible");
		if (!verificarNoDuplicidadNombreUsuario(nuevoUsuario.nombre()))
			throw new ExcepcionPersonalizada("nombre_usuario", "El nombre de usuario ya está en uso.");
		usuarioRepository.guardarUsuario(usuario);
		usuarioRepository.listarUsuarios();
	}

	public void modificarUsuario(UsuarioUpdateRequest modificadoUsuario) {// para modificar el usuario se deberia mandar
																			// la contrase;a como confirmacion
		// if (correo == null || nuevoCorreo == null || correo.equals(nuevoCorreo))
		Usuario usuario = this.usuarioRepository.obtenerUsuario(modificadoUsuario.id()).get();
		if (usuario == null) {
			System.out.println("no existe ese id de usuario");
		}
		if (usuario.getContrasena().equals(modificadoUsuario.contrasena())) {
			System.out.println(
					"antigua: " + usuario.getContrasena() + "enviada x cliente:" + modificadoUsuario.contrasena());
			if (verificarNoDuplicidadCorreo(modificadoUsuario.correo()))
				usuario.setMail(modificadoUsuario.correo());
			else
				throw new RuntimeException("Email ya registrado");// System.out.println("correo ya existente");
			if (verificarNoDuplicidadNombreUsuario(modificadoUsuario.nombre()))
				usuario.setNombreUsuario(modificadoUsuario.nombre());
			else
				throw new RuntimeException("Nombre de usuario ya registrado");// System.out.println("Nombre de usuario
																				// ya existente");
			if (modificadoUsuario.nuevaContrasena() != null)
				usuario.setContrasena(modificadoUsuario.nuevaContrasena());
			if (modificadoUsuario.rol() != null)
				usuario.setRol(convertirRolUsuario(modificadoUsuario.rol()));
			this.usuarioRepository.guardarUsuario(usuario);
		}
		usuarioRepository.listarUsuarios();
		// en patch se debe devolver el recurso modificado.
	}

	public void eliminarUsuarioLogico() {
		// pendiente agregar attr rol y si esta activo el usuario para poder hacer
		// eliminacion logica.
	}

	private boolean verificarNoDuplicidadCorreo(String nuevoCorreo) {
		if (nuevoCorreo == null || nuevoCorreo == "")
			return false;
		if (this.usuarioRepository.verificarCorreo(nuevoCorreo))
			return false;// throw new RuntimeException("Email ya registrado");
		return true;

	}

	private boolean verificarNoDuplicidadNombreUsuario(String nuevoNombreUsuario) {
		if (nuevoNombreUsuario == null || nuevoNombreUsuario == "")
			return false;
		if (this.usuarioRepository.verificarNombreUsuario(nuevoNombreUsuario))
			return false;
		return true;
	}

	private RolUsuario convertirRolUsuario(String rol) {
		try {
			return RolUsuario.valueOf(rol.toUpperCase());
		} catch (IllegalArgumentException e) {
			throw new ExcepcionPersonalizada("Rol de empleado inválido: ",
					". Valores válidos: " + Arrays.toString(RolUsuario.values()));
		}
	}
}
