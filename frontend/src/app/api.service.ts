// api.service.ts
import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Sucursal } from './models/sucursal.model';
import { Rol } from './models/rol.model';
import { Paquete } from './models/paquete.model';

export interface PaqueteRequest {
  remitenteDUIOTelefono: string;
  destinatarioDUIOTelefono: string;
  sucursalOrigen: number;
  sucursalDestino: number;
  descripcion: string;
  peso: number;
  cantidad: Number,
  precio: Number,
  estado: string;
}
export interface EmpleadoRequest {
  nombres: String,
  apellidos: String,
  correo: String,
  sucursal: Number,
  cargo: String,
  usuario: String,
  contrasena: String
}

@Injectable({
  providedIn: 'root',
})
export class ApiService {
  private APIURL = 'http://localhost:8080' as const;

  constructor(private http: HttpClient) { }
// api.service.ts - Agregar estos métodos

public obtenerTodosLosPaquetes(): Observable<Paquete[]> {
    console.log('Obteniendo todos los paquetes...');
    return this.http.get<Paquete[]>(`${this.APIURL}/paquetes/todos`);
}

public buscarPaquetePorCodigo(codigo: string): Observable<Paquete> {
    console.log('Buscando paquete por codigo:', codigo);
    return this.http.get<Paquete>(`${this.APIURL}/paquetes/buscar/${codigo}`);
}

public actualizarEstadoPaquete(id: number, nuevoEstado: string, sucursalId: number): Observable<any> {
    console.log('Actualizando estado del paquete:', id, 'a', nuevoEstado);
    const body = {
        nuevoEstado: nuevoEstado,
        sucursalId: sucursalId
    };
    return this.http.put(`${this.APIURL}/paquetes/${id}/estado`, body);
}

  // Buscar cliente por DUI o teléfono
  public buscarCliente(termino: string = ''): Observable<{ [key: number]: string }> {
    let parametro = new HttpParams();
    parametro = parametro.set('termino', termino);
    console.log(parametro);
    return this.http.get<{ [key: number]: string }>(`${this.APIURL}/paquetes/buscar`, { params: parametro });
  }
//Guardar nuevo paquete
  public guardarPaquete(paquete: PaqueteRequest): Observable<any> {
    console.log("Enviando POST a:", `${this.APIURL}/paquetes`);
    console.log("Datos enviados:", paquete);
    return this.http.post(`${this.APIURL}/paquetes`, paquete);
  }

  public guardarEmpleado(empleado: EmpleadoRequest): Observable<any> {
    console.log("Enviando POST a:", `${this.APIURL}/usuarios`);
    console.log("Datos enviados:", empleado);
    return this.http.post(`${this.APIURL}/usuarios`, empleado);
  }

  // Probar conexión
  public probandoPeticion(): Observable<string> {
    return this.http.get(`${this.APIURL}/test`, { responseType: 'text' });
  }

  // Obtener sucursales disponibles
  public obtenerSucursalesDisponibles(): Observable<{ [key: number]: string }> {
    return this.http.get<{ [key: number]: string }>(`${this.APIURL}/sucursales/disponibles`);
  }

  //EMPLEADOS END-POINT CONSUMER
  public obtenerRolesDisponibles(): Observable<Rol[]> {
    console.log("Obteniendo roles disponibles...");
    return this.http.get<Rol[]>(`${this.APIURL}/roles/disponibles`);
  }
}