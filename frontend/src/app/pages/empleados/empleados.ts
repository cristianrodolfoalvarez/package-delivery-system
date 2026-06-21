import { CommonModule } from '@angular/common';
import { Component, signal, WritableSignal } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ApiService } from '../../api.service';
import { Rol } from '../../models/rol.model';

@Component({
  selector: 'app-empleados',
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './empleados.html',
  styleUrl: './empleados.css',
})
export class Empleados {
<<<<<<< HEAD

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
=======
  registrarUsuarioForm: FormGroup;
  roles: WritableSignal<Rol[]> = signal([]);
  errorMessage: WritableSignal<string> = signal('');
  successMessage: string = '';
  sucursalesArray !: { id: number, nombre: string }[];
  constructor(private api: ApiService) {
    this.registrarUsuarioForm = this.initRegistrarPaqueteForm();
  }
  ngOnInit() {
    this.cargarSucursalesDisponibles();
    this.cargarRoles();
  }
  cargarSucursalesDisponibles() {
    this.api.obtenerSucursalesDisponibles().subscribe({
      next: (sucursalesObtenidas: { [key: number]: string }) => {
        console.log(sucursalesObtenidas);
        this.sucursalesArray = Object.entries(sucursalesObtenidas).map(([id, nombre]) => ({
          id: Number(id),
          nombre: nombre as string
        }));
      },
      error: (err: any) => {
        console.error('Error:', err);
        //this.errorMessage = 'Error al cargar sucursales';
      }
    });
  }
  onSubmit() {
    this.errorMessage.set('');
    console.log('click');
    console.log(typeof this.registrarUsuarioForm.value.contrasena);

    if (this.registrarUsuarioForm.valid) {
      const nuevoEmpleado = {
        nombres: this.registrarUsuarioForm.value.nombres,
        apellidos: this.registrarUsuarioForm.value.apellidos,
        correo: this.registrarUsuarioForm.value.correo,
        sucursal: Number(this.registrarUsuarioForm.value.sucursal),
        cargo: this.registrarUsuarioForm.value.cargo,
        usuario: this.registrarUsuarioForm.value.usuario,
        contrasena: this.registrarUsuarioForm.value.contrasena
      };
      console.log("=== ONSUBMIT EJECUTADO ===");
      console.log("Formulario válido?", this.registrarUsuarioForm.valid);
      console.log("Valores del formulario:", this.registrarUsuarioForm.value);

      this.api.guardarEmpleado(nuevoEmpleado).subscribe({
        next: (response: any) => {
          console.log('Respuesta del servidor:', response);
          //this.isLoading = false;
          //this.successMessage = 'Paquete registrado';

          setTimeout(() => {
            // this.resetFormulario();
          }, 2000);
        },
        error: (error: any) => {
          //console.error('Error al guardar:', error);
          //this.isLoading = false;
          let mensaje = 'Error al crear el usuario';
   if (error.status === 400) {
            if (error.error && typeof error.error === 'object') {
              mensaje = error.error.message || 
                        error.error.mensaje || 
                        error.error.error ||
                        Object.values(error.error).join(', ');
            } else if (typeof error.error === 'string') {
              mensaje = error.error;
            }
          } else if (error.status === 409) {
            mensaje = 'El correo electrónico ya está registrado.';
          } else if (error.status === 0) {
            mensaje = 'No se pudo conectar con el servidor.';
          }
          this.errorMessage.set(mensaje);
        }
      });
    }

  }
  private initRegistrarPaqueteForm(): FormGroup {
    const CORREO_VALIDACION = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
    return new FormGroup({
      nombres: new FormControl('aaa', [Validators.required, Validators.pattern('^[a-zA-Z]{3,25}$')]),
      apellidos: new FormControl('aaa', [Validators.required, Validators.pattern('^[a-zA-Z]{3,25}$')]),
      correo: new FormControl('aaa@aaa.com', [Validators.required, Validators.pattern(CORREO_VALIDACION)]),
      sucursal: new FormControl('', [Validators.required]),
      cargo: new FormControl('', [Validators.required]),
      usuario: new FormControl('aaa', [Validators.required, Validators.pattern('^[a-z][a-z0-9]{2,15}$')]),
      contrasena: new FormControl('aaaQ3$',
        [Validators.required, Validators.pattern(
          '^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{6,24}$')])
    });
  }
  cargarRoles() {
    // this.isLoading = true;
    this.api.obtenerRolesDisponibles().subscribe({
      next: (roles) => {
        console.log('Roles recibidos:', roles);
       this.roles.set(roles);
        // this.isLoading = false;
      },
      error: (error) => {
        console.error('Error al cargar roles:', error);
        // this.errorMessage = 'Error al cargar los roles';
        //this.isLoading = false;
      }
    });
  }
}
>>>>>>> bdcb3e8d6c0a57a3ff04cc6d6e1288a8b5d311fa
