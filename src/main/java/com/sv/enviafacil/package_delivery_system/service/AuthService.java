// service/AuthService.java
package com.sv.enviafacil.package_delivery_system.service;

import com.sv.enviafacil.package_delivery_system.dto.request.InicioSesionRequest;
import com.sv.enviafacil.package_delivery_system.dto.response.InicioSesionResponse;
import com.sv.enviafacil.package_delivery_system.model.Usuario;
import com.sv.enviafacil.package_delivery_system.repository.UsuarioRepository;
import com.sv.enviafacil.package_delivery_system.utils.ContrasenaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private ContrasenaUtil ContrasenaUtil;

    public InicioSesionResponse autenticar(InicioSesionRequest request) {
        System.out.println("=== AUTENTICANDO USUARIO ===");
        System.out.println("Usuario: " + request.usuario());
        
        // Buscar usuario
        Usuario usuario = usuarioRepository.buscarPorUsuario(request.usuario());
        
        if (usuario == null) {
            System.out.println("Usuario no encontrado");
            return new InicioSesionResponse(false, "Usuario o contraseña incorrectos", null, null, null);
        }
        
        // Verificar contraseña
        boolean passwordValida = ContrasenaUtil.verificar(request.contrasena(), usuario.getContrasena());
        
        if (!passwordValida) {
            System.out.println("Contraseña incorrecta");
            return new InicioSesionResponse(false, "Usuario o contraseña incorrectos", null, null, null);
        }
        
        System.out.println("Login exitoso: " + usuario.getNombreUsuario());
        
        return new InicioSesionResponse(
            true,
            "Login exitoso",
            usuario.getNombreUsuario(),
            usuario.getRol().name(),
            usuario.getId()
        );
    }
}
