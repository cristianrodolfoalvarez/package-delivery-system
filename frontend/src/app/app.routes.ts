import { Routes } from '@angular/router';
import { Inicio } from './pages/inicio/inicio';
import { Clientes } from './pages/clientes/clientes';
import { RegistrarSucursal } from './pages/registrar-sucursal/registrar-sucursal';
import { RegistrarPaquete } from './pages/registrar-paquete/registrar-paquete';
import { ConsultarPaquetes } from './pages/consultar-paquetes/consultar-paquetes';
import { Empleados } from './pages/empleados/empleados';
import { AsignacionRuta } from './pages/asignacion-ruta/asignacion-ruta';
import { Login } from './pages/login/login';

export const routes: Routes = [

  {
    path: '',
    component: Login
  },

  {
    path: 'inicio',
    component: Inicio
  },

  {
    path: 'clientes',
    component: Clientes
  },

  {
    path: 'test',
    component: Clientes
  },

  {
    path: 'registrar-sucursal',
    component: RegistrarSucursal
  },

  {
    path: 'registrar-paquete',
    component: RegistrarPaquete
  },

  {
    path: 'consultar-paquetes',
    component: ConsultarPaquetes
  },

  {
    path: 'empleados',
    component: Empleados
  },

  {
    path: 'asignacion-ruta',
    component: AsignacionRuta
  }

];