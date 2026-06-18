import { ApiService } from '../../api.service';
import { CommonModule } from '@angular/common';
import { Component, OnInit, signal, WritableSignal } from '@angular/core';
import { ReactiveFormsModule, FormControl, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-registrar-paquete',
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './registrar-paquete.html',
  styleUrl: './registrar-paquete.css',
})
export class RegistrarPaquete implements OnInit {
  // Signals para valores que se actualizan automáticamente
  remitenteSeleccionado: WritableSignal<string> = signal('');
  destinatarioSeleccionado: WritableSignal<string> = signal('');
  
  // Variables con valores iniciales
  isLoading: boolean = false;
  errorMessage: string = '';
  successMessage: string = '';

  registrarPaqueteForm: FormGroup;
  sucursalesArray: { id: number, nombre: string }[] = [];

  constructor(private api: ApiService) {
    this.registrarPaqueteForm = this.initRegistrarPaqueteForm();
  }

  ngOnInit() {
    this.cargarSucursalesDisponibles();
  }

  private initRegistrarPaqueteForm(): FormGroup {
    return new FormGroup({
      remitenteDUIOTelefono: new FormControl('', [Validators.required, Validators.pattern('^[0-9]{8,9}$')]),
      destinatarioDUIOTelefono: new FormControl('', [Validators.required, Validators.pattern('^[0-9]{8,9}$')]),
      sucursalOrigen: new FormControl('', [Validators.required]),
      sucursalDestino: new FormControl('', [Validators.required]),
      descripcion: new FormControl('', ),
      peso: new FormControl(1, [Validators.required, Validators.min(1)]),
      estado: new FormControl('Pendiente', [Validators.required])
    });
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
        this.errorMessage = 'Error al cargar sucursales';
      }
    });
  }

  buscarClienteRemitenteEnSelect(duiOTelefono: string) {
    console.log("click detectado, valor recibido:", duiOTelefono);
    
    let terminoBusqueda = duiOTelefono?.trim();
    
    if (!terminoBusqueda) {
      this.remitenteSeleccionado.set('');
      return;
    }
    
    const regex = /^[0-9]{8,9}$/;
    if (!regex.test(terminoBusqueda)) {
      this.remitenteSeleccionado.set('Formato inválido (8-9 dígitos)');
      return;
    }
    
    console.log("Buscando cliente:", terminoBusqueda);
    
    this.api.buscarCliente(terminoBusqueda).subscribe({
      next: (respuesta: { [key: number]: string }) => {
        console.log("Respuesta completa:", respuesta);
        
        let nombreCliente = '';
        
        if (respuesta && typeof respuesta === 'object') {
          const valores = Object.values(respuesta);
          if (valores.length > 0) {
            nombreCliente = valores[0] as string;
            const partes = nombreCliente.split(',');
            if (partes.length >= 2) {
              partes.pop();
              nombreCliente = partes.join(',').trim();
            }
          } else {
            nombreCliente = 'Cliente encontrado';
          }
          
          console.log("Nombre extraído:", nombreCliente);
        } else {
          nombreCliente = 'Cliente encontrado';
        }
        
        this.remitenteSeleccionado.set(nombreCliente);
      },
      error: (error: any) => {
        console.error("Error en la petición:", error);
        this.remitenteSeleccionado.set('Error: Cliente no encontrado');
      }
    });
  }

  buscarClienteDestinatario(duiOTelefono: string) {
    console.log("click detectado destinatario, valor recibido:", duiOTelefono);
    
    let terminoBusqueda = duiOTelefono?.trim();
    
    if (!terminoBusqueda) {
      this.destinatarioSeleccionado.set('');
      return;
    }
    
    const regex = /^[0-9]{8,9}$/;
    if (!regex.test(terminoBusqueda)) {
      this.destinatarioSeleccionado.set('Formato inválido (8-9 dígitos)');
      return;
    }
    
    console.log("Buscando destinatario:", terminoBusqueda);
    
    this.api.buscarCliente(terminoBusqueda).subscribe({
      next: (respuesta: { [key: number]: string }) => {
        console.log("Respuesta destinatario:", respuesta);
        
        let nombreCliente = '';
        
        if (respuesta && typeof respuesta === 'object') {
          const valores = Object.values(respuesta);
          if (valores.length > 0) {
            nombreCliente = valores[0] as string;
            const partes = nombreCliente.split(',');
            if (partes.length >= 2) {
              partes.pop();
              nombreCliente = partes.join(',').trim();
            }
          } else {
            nombreCliente = 'Cliente encontrado';
          }
        } else {
          nombreCliente = 'Cliente encontrado';
        }
        
        this.destinatarioSeleccionado.set(nombreCliente);
      },
      error: (error: any) => {
        console.error("Error:", error);
        this.destinatarioSeleccionado.set('Error: Cliente no encontrado');
      }
    });
  }

  onSubmit() {
    // Limpiar mensajes previos
    this.errorMessage = '';
    this.successMessage = '';
        console.log("=== ONSUBMIT EJECUTADO ===");
    console.log("Formulario válido?", this.registrarPaqueteForm.valid);
    console.log("Valores del formulario:", this.registrarPaqueteForm.value);
    console.log("Remitente seleccionado:", this.remitenteSeleccionado());
    console.log("Destinatario seleccionado:", this.destinatarioSeleccionado());
    // Validar que se haya encontrado un remitente
    if (!this.remitenteSeleccionado() || 
        this.remitenteSeleccionado() === 'Cliente no encontrado' || 
        this.remitenteSeleccionado() === 'Formato inválido (8-9 dígitos)' ||
        this.remitenteSeleccionado() === 'Error: Cliente no encontrado') {
      this.errorMessage = 'Debe buscar y seleccionar un remitente válido';
      return;
    }
    
    // Validar que se haya encontrado un destinatario
    if (!this.destinatarioSeleccionado() || 
        this.destinatarioSeleccionado() === 'Cliente no encontrado' || 
        this.destinatarioSeleccionado() === 'Formato inválido (8-9 dígitos)' ||
        this.destinatarioSeleccionado() === 'Error: Cliente no encontrado') {
      this.errorMessage = 'Debe buscar y seleccionar un destinatario válido';
      return;
    }
    
    if (this.registrarPaqueteForm.valid) {
      this.isLoading = true;
      
      const paqueteData = {
        remitenteDUIOTelefono: this.registrarPaqueteForm.value.remitenteDUIOTelefono,
        destinatarioDUIOTelefono: this.registrarPaqueteForm.value.destinatarioDUIOTelefono,
        sucursalOrigen: Number(this.registrarPaqueteForm.value.sucursalOrigen),
        sucursalDestino: Number(this.registrarPaqueteForm.value.sucursalDestino),
        descripcion: this.registrarPaqueteForm.value.descripcion,
        peso: Number(this.registrarPaqueteForm.value.peso),
        estado: this.registrarPaqueteForm.value.estado
      };
      
      console.log("Enviando paquete al backend:", paqueteData);
      
      this.api.guardarPaquete(paqueteData).subscribe({
        next: (response: any) => {
          console.log('Respuesta del servidor:', response);
          this.isLoading = false;
          this.successMessage = '¡Paquete registrado exitosamente!';
          
          setTimeout(() => {
            this.resetFormulario();
          }, 2000);
        },
        error: (error: any) => {
          console.error('Error al guardar:', error);
          this.isLoading = false;
          
          if (error.status === 400) {
            if (error.error && typeof error.error === 'object') {
              const errores = Object.values(error.error).join(', ');
              this.errorMessage = `Error de validación: ${errores}`;
            } else {
              this.errorMessage = 'Error en los datos enviados. Verifique la información.';
            }
          } else if (error.status === 404) {
            this.errorMessage = 'Sucursal no encontrada.';
          } else if (error.status === 0) {
            this.errorMessage = 'No se pudo conectar con el servidor. ¿Spring Boot está corriendo?';
          } else {
            this.errorMessage = `Error: ${error.message || 'Error al guardar el paquete'}`;
          }
        }
      });
      
    } else {
      console.log("Formulario inválido");
      Object.keys(this.registrarPaqueteForm.controls).forEach(key => {
        const control = this.registrarPaqueteForm.get(key);
        if (control) {
          control.markAsTouched();
        }
      });
      this.errorMessage = 'Por favor, complete todos los campos requeridos correctamente.';
    }
  }

  resetFormulario() {
    this.registrarPaqueteForm.reset({
      remitenteDUIOTelefono: '',
      destinatarioDUIOTelefono: '',
      sucursalOrigen: '',
      sucursalDestino: '',
      descripcion: '',
      peso: 1,
      estado: 'Pendiente'
    });
    this.remitenteSeleccionado.set('');
    this.destinatarioSeleccionado.set('');
    this.successMessage = '';
    this.errorMessage = '';
    this.isLoading = false;
  }

  volverInicio() {
    window.location.href = '/';
  }
}