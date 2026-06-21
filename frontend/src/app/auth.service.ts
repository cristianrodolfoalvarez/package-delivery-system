// auth.service.ts
import { Injectable, Inject, PLATFORM_ID } from '@angular/core';
import { isPlatformBrowser } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { tap } from 'rxjs/operators';

export interface LoginRequest {
  usuario: string;
  contrasena: string;
}

export interface LoginResponse {
  esValido: boolean;
  mensaje: string;
  usuario: string | null;
  rol: string | null;
  id: number | null;
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private APIURL = 'http://localhost:8080';
  private isBrowser: boolean;

  constructor(
    private http: HttpClient,
    @Inject(PLATFORM_ID) private platformId: Object
  ) {
    this.isBrowser = isPlatformBrowser(platformId);
  }

  login(credentials: LoginRequest): Observable<LoginResponse> {
    console.log('Enviando login:', credentials);
    return this.http.post<LoginResponse>(`${this.APIURL}/auth/login`, credentials)
      .pipe(
        tap(response => {
          console.log('Respuesta del servidor:', response);
          if (response.esValido) {
            this.guardarSesion(response);
          }
        })
      );
  }

  private guardarSesion(response: LoginResponse): void {
    if (!this.isBrowser) return;
    
    localStorage.setItem('usuario', response.usuario || '');
    localStorage.setItem('rol', response.rol || '');
    localStorage.setItem('userId', String(response.id || ''));
    localStorage.setItem('isLoggedIn', 'true');
    
    console.log('Sesión guardada correctamente');
  }

  obtenerUsuario(): string | null {
    if (!this.isBrowser) return null;
    return localStorage.getItem('usuario');
  }

  obtenerRol(): string | null {
    if (!this.isBrowser) return null;
    return localStorage.getItem('rol');
  }

  obtenerUserId(): number | null {
    if (!this.isBrowser) return null;
    const id = localStorage.getItem('userId');
    return id ? Number(id) : null;
  }

  isAuthenticated(): boolean {
    if (!this.isBrowser) return false;
    return localStorage.getItem('isLoggedIn') === 'true';
  }

  //Cierra sesión y limpia el localStorage
  logout(): void {
    console.log('🚪 Cerrando sesión...');
    
    if (!this.isBrowser) return;
    
    localStorage.removeItem('usuario');
    localStorage.removeItem('rol');
    localStorage.removeItem('userId');
    localStorage.removeItem('isLoggedIn');
    
    console.log('Sesión cerrada correctamente');
  }

  hasRole(rol: string): boolean {
    if (!this.isBrowser) return false;
    return this.obtenerRol() === rol;
  }

  isAdmin(): boolean {
    return this.hasRole('ADMINISTRADOR_SISTEMA');
  }

  isGerente(): boolean {
    return this.hasRole('GERENTE_SUCURSAL');
  }
}