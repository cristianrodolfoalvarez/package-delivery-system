import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-clientes',
  standalone: true,

  // Importo FormsModule porque estoy utilizando ngModel
  imports: [FormsModule],

  templateUrl: './clientes.html',
  styleUrl: './clientes.css'
})
export class Clientes {

  // Aquí guardo el ID que escribiré para buscar un cliente
  idBuscar: number = 0;

  constructor() {}

  // Este método se ejecuta cuando presiono el botón Buscar
  buscarCliente() {

    // Por ahora solo verifico que el ID se esté capturando correctamente
    console.log('Buscando cliente con ID:', this.idBuscar);

  }

}