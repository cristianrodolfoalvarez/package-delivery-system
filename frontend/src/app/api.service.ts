// api.service.ts
import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Sucursal } from './models/sucursal.model';

export interface PaqueteRequest {
  remitenteDUIOTelefono: string;
  destinatarioDUIOTelefono: string;
  sucursalOrigen: number;
  sucursalDestino: number;
  descripcion: string;
  peso: number;
  estado: string;
}

@Injectable({
  providedIn: 'root',
})
export class ApiService {
  private APIURL = 'http://localhost:8080';
  
  constructor(private http: HttpClient) { }

  // Buscar cliente por DUI o teléfono
  public buscarCliente(termino: string = ''): Observable<{ [key: number]: string }> {
    let parametro = new HttpParams();
    parametro = parametro.set('termino', termino);
    console.log(parametro);
    return this.http.get<{ [key: number]: string }>(`${this.APIURL}/paquetes/buscar`, { params: parametro });
  }

  // ✅ AGREGAR ESTE MÉTODO - Guardar nuevo paquete
  public guardarPaquete(paquete: PaqueteRequest): Observable<any> {
    console.log("Enviando POST a:", `${this.APIURL}/paquetes`);
    console.log("Datos enviados:", paquete);
    return this.http.post(`${this.APIURL}/paquetes`, paquete);
  }

  // Probar conexión
  public probandoPeticion(): Observable<string> {
    return this.http.get(`${this.APIURL}/test`, { responseType: 'text' });
  }

  // Obtener sucursales disponibles
  public obtenerSucursalesDisponibles(): Observable<{ [key: number]: string }> {
    return this.http.get<{ [key: number]: string }>(`${this.APIURL}/sucursales/disponibles`);
  }
}