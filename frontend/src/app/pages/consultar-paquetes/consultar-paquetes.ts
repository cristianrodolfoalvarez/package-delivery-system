import { Component } from '@angular/core';

// Componente encargado de mostrar la consulta y seguimiento de paquetes

@Component({
  selector: 'app-consultar-paquetes',
  standalone: true,
  imports: [],
  templateUrl: './consultar-paquetes.html',
  styleUrl: './consultar-paquetes.css'
})
export class ConsultarPaquetes {

  // Aquí guardaré el ID del paquete que deseo consultar
  idPaqueteBuscar: number = 0;

  constructor(){}

  // Este método se ejecuta al presionar el botón Buscar
  buscarPaquete(id: string){

    // Por ahora solo verifico que el ID llegue correctamente
    // Más adelante aquí se llamará al backend para obtener la información
    console.log('Buscando paquete con ID:', id);

  }

}