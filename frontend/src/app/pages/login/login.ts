/// login.component.ts
import { Component, Inject, PLATFORM_ID } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { CommonModule, isPlatformBrowser } from '@angular/common';
import { AuthService, LoginRequest } from '../../auth.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './login.html',
  styleUrl: './login.css'
})
export class Login {

  usuario: string = '';
  contrasena: string = '';
  mensaje: string = '';
  isLoading: boolean = false;
  private isBrowser: boolean;

  constructor(
    private router: Router,
    private authService: AuthService,
    @Inject(PLATFORM_ID) private platformId: Object
  ) {
    this.isBrowser = isPlatformBrowser(platformId);
    
    // Solo verificar autenticación en el navegador
    if (this.isBrowser && this.authService.isAuthenticated()) {
      this.router.navigate(['/inicio']);
    }
  }

  iniciarSesion() {
    console.log('Intentando login...');
    
    // Limpiar mensaje previo
    this.mensaje = '';
    this.isLoading = true;

    if (!this.usuario || !this.contrasena) {
      this.mensaje = 'Por favor, ingrese usuario y contraseña';
      this.isLoading = false;
      return;
    }

    const credentials: LoginRequest = {
      usuario: this.usuario,
      contrasena: this.contrasena
    };

    this.authService.login(credentials).subscribe({
      next: (response) => {
        console.log('Respuesta del servidor:', response);
        this.isLoading = false;

        if (response.esValido) {
          console.log('✅ Login exitoso - Rol:', response.rol);
          this.router.navigate(['/inicio']);
        } else {
          this.mensaje = response.mensaje || 'Usuario o contraseña incorrectos';
        }
      },
      error: (error) => {
        console.error('Error de login:', error);
        this.isLoading = false;
        
        if (error.status === 0) {
          this.mensaje = 'No se pudo conectar con el servidor. ¿Spring Boot está corriendo?';
        } else if (error.status === 400) {
          this.mensaje = 'Datos de login inválidos';
        } else if (error.status === 401) {
          this.mensaje = 'Usuario o contraseña incorrectos';
        } else {
          this.mensaje = 'Error al iniciar sesión. Intente nuevamente.';
        }
      }
    });
  }

  limpiarMensaje() {
    this.mensaje = '';
  }
}