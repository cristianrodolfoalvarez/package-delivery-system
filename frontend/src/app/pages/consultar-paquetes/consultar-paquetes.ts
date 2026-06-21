// consultar-paquetes.component.ts
import { Component, OnInit, signal, WritableSignal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ApiService } from '../../api.service';

export interface PuntoTransito {
    fecha: string;
    sucursal: string;
    estado: string;
}

export interface Paquete {
    id: number;
    codigoSeguimiento: string;
    remitente: string;
    destinatario: string;
    estado: string;  
    sucursalOrigen: string;
    sucursalDestino: string;
    ubicacionActual: string;
    puntosDeTransito: PuntoTransito[];
}

@Component({
  selector: 'app-consultar-paquetes',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './consultar-paquetes.html',
  styleUrl: './consultar-paquetes.css'
})
export class ConsultarPaquetes implements OnInit {
  paquetes: WritableSignal<Paquete[]> = signal([]);
  paquetesFiltrados: WritableSignal<Paquete[]> = signal([]);
  codigoBusqueda: string = '';
  isLoading: WritableSignal<boolean> = signal(false);
  errorMessage: WritableSignal<string> = signal('');
  successMessage: WritableSignal<string> = signal('');
  paqueteSeleccionado: WritableSignal<Paquete | null> = signal(null);
  mostrarDetalle: WritableSignal<boolean> = signal(false);

  estadosDisponibles = [
    { valor: 'PENDIENTE', etiqueta: 'Pendiente' },
    { valor: 'EN_RUTA', etiqueta: 'En Ruta' },
    { valor: 'LLEGO_A_ESCALA', etiqueta: 'Llego a Escala' },
    { valor: 'DISPONIBLE_PARA_RETIRO', etiqueta: 'Disponible para Retiro' },
    { valor: 'ENTREGADO', etiqueta: 'Entregado' },
    { valor: 'ANULADO', etiqueta: 'Anulado' }
  ];

  private estadoMap: { [key: string]: string } = {
    'PENDIENTE': 'Pendiente',
    'EN_RUTA': 'En Ruta',
    'LLEGO_A_ESCALA': 'Llego a Escala',
    'DISPONIBLE_PARA_RETIRO': 'Disponible para Retiro',
    'ENTREGADO': 'Entregado',
    'ANULADO': 'Anulado'
  };

  constructor(private api: ApiService) {}

  ngOnInit() {
    this.cargarTodosLosPaquetes();
  }

  cargarTodosLosPaquetes() {
    this.isLoading.set(true);
    this.errorMessage.set('');
    
    this.api.obtenerTodosLosPaquetes().subscribe({
      next: (paquetes) => {
        this.paquetes.set(paquetes || []);
        this.paquetesFiltrados.set([...this.paquetes()]);
        this.isLoading.set(false);
        console.log('Paquetes cargados:', this.paquetesFiltrados().length);
      },
      error: (error) => {
        console.error('Error al cargar paquetes:', error);
        this.errorMessage.set('Error al cargar los paquetes');
        this.isLoading.set(false);
      }
    });
  }

  buscarPorCodigo() {
    if (!this.codigoBusqueda || this.codigoBusqueda.trim() === '') {
      this.paquetesFiltrados.set([...this.paquetes()]);
      return;
    }

    this.isLoading.set(true);
    const codigo = this.codigoBusqueda.trim();
    
    this.api.buscarPaquetePorCodigo(codigo).subscribe({
      next: (paquete) => {
        this.paquetesFiltrados.set(paquete ? [paquete] : []);
        this.isLoading.set(false);
        
        if (this.paquetesFiltrados().length === 0) {
          this.errorMessage.set('No se encontro ningun paquete con ese codigo');
        } else {
          this.errorMessage.set('');
        }
      },
      error: (error) => {
        console.error('Error en busqueda:', error);
        this.paquetesFiltrados.set([]);
        this.errorMessage.set('Paquete no encontrado');
        this.isLoading.set(false);
      }
    });
  }

  actualizarEstado(paquete: Paquete, nuevoEstado: string) {
    console.log('Estado actual:', paquete.estado);
    console.log('Nuevo estado seleccionado:', nuevoEstado);
    
    if (paquete.estado === nuevoEstado) {
      console.log('El estado no ha cambiado');
      return;
    }
    
    const sucursalId = 1;
    
    this.api.actualizarEstadoPaquete(paquete.id, nuevoEstado, sucursalId).subscribe({
      next: () => {
        paquete.estado = nuevoEstado;
        this.successMessage.set('Estado actualizado a: ' + this.getEstadoDescripcion(nuevoEstado));
        this.cargarTodosLosPaquetes();
        
        setTimeout(() => {
          this.successMessage.set('');
        }, 3000);
      },
      error: (error) => {
        console.error('Error al actualizar estado:', error);
        this.errorMessage.set('Error al actualizar el estado del paquete');
        
        setTimeout(() => {
          this.errorMessage.set('');
        }, 3000);
      }
    });
  }

  getEstadoDescripcion(estado: string): string {
    return this.estadoMap[estado] || estado;
  }

  getEstadoClass(estado: string): string {
    return 'estado ' + estado;
  }

  verDetalle(paquete: Paquete) {
    this.paqueteSeleccionado.set(paquete);
    this.mostrarDetalle.set(true);
  }

  cerrarDetalle() {
    this.mostrarDetalle.set(false);
    this.paqueteSeleccionado.set(null);
  }

  limpiarMensajes() {
    this.errorMessage.set('');
    this.successMessage.set('');
  }

  volverInicio() {
    window.location.href = '/inicio';
  }
}