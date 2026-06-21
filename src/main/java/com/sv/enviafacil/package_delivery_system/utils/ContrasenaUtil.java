package com.sv.enviafacil.package_delivery_system.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class ContrasenaUtil {
    
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    
    /**
     * debemos de encriptar. C_Alvarez.
     */
    public String encriptar(String contrasena) {
        return encoder.encode(contrasena);
    }
    
    /**
     * verificmos en plane text
     */
    public boolean verificar(String contrasenaPlano, String contrasenaHash) {
        return encoder.matches(contrasenaPlano, contrasenaHash);
    }
}
