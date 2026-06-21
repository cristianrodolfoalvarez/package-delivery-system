import { Component } from '@angular/core';

@Component({
  selector: 'app-empleados',
  imports: [],
  templateUrl: './empleados.html',
  styleUrl: './empleados.css',
})
export class Empleados {

  // Aquí guardaré el ID del empleado que quiero consultar
  idBuscar: number = 0;

  constructor() {}

  // Este método se ejecuta cuando presiono el botón Buscar
  buscarEmpleado() {

    // Por ahora solo verifico que el ID se esté capturando correctamente
    // Más adelante aquí se llamará al backend
    console.log('Buscando empleado con ID:', this.idBuscar);

  }

}