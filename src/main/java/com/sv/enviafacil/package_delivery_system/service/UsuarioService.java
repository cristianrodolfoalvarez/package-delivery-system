package com.sv.enviafacil.package_delivery_system.service;

import org.springframework.stereotype.Service;

import com.sv.enviafacil.package_delivery_system.dto.request.UsuarioCreateRequest;
import com.sv.enviafacil.package_delivery_system.dto.request.UsuarioUpdateRequest;
import com.sv.enviafacil.package_delivery_system.model.Usuario;
import com.sv.enviafacil.package_delivery_system.repository.UsuarioRepository;

@Service
public class UsuarioService {
	private UsuarioRepository usuarioRepository;

	public UsuarioService(UsuarioRepository usuarioRepository) {
		this.usuarioRepository = usuarioRepository;
	}

	public void crearUsuario(UsuarioCreateRequest nuevoUsuario) {
		Usuario usuario = new Usuario(nuevoUsuario.correo(), nuevoUsuario.nombre(), nuevoUsuario.contrasena(),
				"JWTOKEN");
		if (!verificarNoDuplicidadCorreo(nuevoUsuario.correo())
				|| !verificarNoDuplicidadNombreUsuario(nuevoUsuario.nombre()))
			System.out.println("correo electronico y/o nombre de usuario no disponible");
		else
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
		if (usuario.getContrasena().equals(modificadoUsuario.contrasena())){
			System.out.println("antigua: "+usuario.getContrasena()+"enviada x cliente:"+modificadoUsuario.contrasena());
			if (verificarNoDuplicidadCorreo(modificadoUsuario.correo()))
				usuario.setMail(modificadoUsuario.correo());
			else
				System.out.println("correo ya existente");
			if (verificarNoDuplicidadNombreUsuario(modificadoUsuario.nombre()))
				usuario.setNombreUsuario(modificadoUsuario.nombre());
			else
				System.out.println("Nombre de usuario ya existente");
			if(modificadoUsuario.nuevaContrasena()!= null)
				usuario.setContrasena(modificadoUsuario.nuevaContrasena());
			this.usuarioRepository.guardarUsuario(usuario);
		}
		usuarioRepository.listarUsuarios();
		// en patch se debe devolver el recurso modificado.
	}
	
	public void eliminarUsuarioLogico() {
		//pendiente agregar attr rol y si esta activo el usuario para poder hacer eliminacion logica.
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
}
