// clientes.component.ts
import { Component, OnInit, signal, WritableSignal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule, FormGroup, FormControl, Validators } from '@angular/forms';
import { ApiService } from '../../api.service';
import { Cliente, ClienteCreateRequest } from '../../models/cliente.model';

@Component({
  selector: 'app-clientes',
  standalone: true,
  imports: [CommonModule, FormsModule, ReactiveFormsModule],
  templateUrl: './clientes.html',
  styleUrl: './clientes.css'
})
export class Clientes implements OnInit {
  clienteForm: FormGroup;
  isLoading: WritableSignal<boolean> = signal(false);
  errorMessage: WritableSignal<string> = signal('');
  successMessage: WritableSignal<string> = signal('');
  clientes: WritableSignal<Cliente[]> = signal([]);
  mostrarLista: boolean = false;

  constructor(private api: ApiService) {
    this.clienteForm = this.initClienteForm();
  }

  ngOnInit() {
    this.cargarClientes();
  }

  private initClienteForm(): FormGroup {
    return new FormGroup({
      nombres: new FormControl('', [
        Validators.required, 
        Validators.minLength(3),
        Validators.pattern('^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]{3,25}$')
      ]),
      apellidos: new FormControl('', [
        Validators.required, 
        Validators.minLength(3),
        Validators.pattern('^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]{3,25}$')
      ]),
      dui: new FormControl('', [
        Validators.required, 
        Validators.pattern('^[0-9]{9}$')
      ]),
      telefono: new FormControl('', [
        Validators.required, 
        Validators.pattern('^[0-9]{8}$')
      ]),
      correo: new FormControl('', [
        Validators.required, 
        Validators.email,
        Validators.pattern('^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$')
      ])
    });
  }

  cargarClientes() {
    this.api.obtenerTodosClientes().subscribe({
      next: (clientes) => {
        this.clientes.set(clientes);
        console.log('Clientes cargados:', clientes.length);
      },
      error: (error) => {
        console.error('Error al cargar clientes:', error);
      }
    });
  }

  toggleLista() {
    this.mostrarLista = !this.mostrarLista;
    if (this.mostrarLista) {
      this.cargarClientes();
    }
  }

  onSubmit() {
    this.errorMessage.set('');
    this.successMessage.set('');

    if (this.clienteForm.invalid) {
      Object.keys(this.clienteForm.controls).forEach(key => {
        this.clienteForm.get(key)?.markAsTouched();
      });
      this.errorMessage.set('Por favor, complete todos los campos correctamente.');
      return;
    }

    this.isLoading.set(true);

    const clienteData: ClienteCreateRequest = {
      correo: this.clienteForm.value.correo,
      nombres: this.clienteForm.value.nombres,
      apellidos: this.clienteForm.value.apellidos,
      telefono: this.clienteForm.value.telefono,
      dui: this.clienteForm.value.dui,
      activo: true
    };

    this.api.crearCliente(clienteData).subscribe({
      next: (cliente) => {
        console.log('Cliente creado:', cliente);
        this.isLoading.set(false);
        this.successMessage.set('Cliente registrado exitosamente');
        this.cargarClientes();
        this.clienteForm.reset();
        
        setTimeout(() => {
          this.successMessage.set('');
        }, 3000);
      },
      error: (error) => {
        console.error('Error al crear cliente:', error);
        this.isLoading.set(false);
        
        if (error.status === 409) {
          this.errorMessage.set(error.error || 'El cliente ya existe con ese DUI, telefono o correo');
        } else if (error.status === 400) {
          this.errorMessage.set('Datos invalidos. Verifique la informacion.');
        } else {
          this.errorMessage.set('Error al crear el cliente. Intente nuevamente.');
        }
        
        setTimeout(() => {
          this.errorMessage.set('');
        }, 4000);
      }
    });
  }

  volverInicio() {
    window.location.href = '/inicio';
  }
}