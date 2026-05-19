package com.sv.enviafacil.package_delivery_system.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.sv.enviafacil.package_delivery_system.model.Usuario;

@Repository
public class UsuarioRepository {
	private List<Usuario> usuarios = new ArrayList<Usuario>();

	public void guardarUsuario(Usuario usuario) {
		int indice = this.buscarPorId(usuario.getId());
		if (this.buscarPorId(usuario.getId()) == -1) {
			this.usuarios.add(usuario);
			System.out.println("es nuevo");
		}
		else {
			System.out.println("es existente");
			this.usuarios.set(indice, usuario);
		}
		System.out.println("total usuarios:"+usuarios.size());

	}

	public int buscarPorId(int id) {
		Optional<Usuario> optional = this.usuarios.stream().filter(u -> u.getId() == id).findFirst();
		return optional.isEmpty() ? -1 : this.usuarios.indexOf(optional.get())/* optional.get().getId()*/;
	}
	
	public Optional<Usuario> obtenerUsuario(int id) {
		Optional<Usuario> optional = this.usuarios.stream().filter(u -> u.getId() == id).findFirst();
		return optional;
	}
	
	public void listarUsuarios() {
		this.usuarios.forEach(s -> System.out.println("ID:"+s.getId()+"\tNombre:"+s.getNombreUsuario()+"\tcontrasena:"+s.getContrasena()+"\tcorreo: "+s.getMail()));
	}
	
	//Se debe validar el mail, que no exista tambien.
    public boolean verificarCorreo(String correo) {
        return this.usuarios.stream().anyMatch(m -> m.getMail().equals(correo));
    }
    public boolean verificarNombreUsuario(String nombreUsuario) {
        return this.usuarios.stream().anyMatch(m -> m.getNombreUsuario().equals(nombreUsuario));
    }
}