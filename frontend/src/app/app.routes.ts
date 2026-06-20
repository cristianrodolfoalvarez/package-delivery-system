import { Routes } from '@angular/router';
import { Inicio } from './pages/inicio/inicio';
import { Clientes } from './pages/clientes/clientes';
import { RegistrarSucursal } from './pages/registrar-sucursal/registrar-sucursal';
import { RegistrarPaquete } from './pages/registrar-paquete/registrar-paquete';
import { ConsultarPaquetes } from './pages/consultar-paquetes/consultar-paquetes';
import { Empleados } from './pages/empleados/empleados';
import { AsignacionRuta } from './pages/asignacion-ruta/asignacion-ruta';
import { Login } from './pages/login/login';
import { AuthGuard } from './guards/auth.guards';

export const routes: Routes = [
  { path: '', 
    redirectTo: '/login',
    pathMatch: 'full'
  },

  {
    path: 'login',
    component: Login
  },

  {
    path: 'inicio',
    component: Inicio,
    canActivate: [AuthGuard]
  },

  {
    path: 'clientes',
    component: Clientes,
    canActivate: [AuthGuard]
  },

  {
    path: 'test',
    component: Clientes,
    canActivate: [AuthGuard]
  },

  {
    path: 'registrar-sucursal',
    component: RegistrarSucursal,
    canActivate: [AuthGuard]
  },

  {
    path: 'registrar-paquete',
    component: RegistrarPaquete,
    canActivate: [AuthGuard]
  },

  {
    path: 'consultar-paquetes',
    component: ConsultarPaquetes,
    canActivate: [AuthGuard]
  },

  {
    path: 'empleados',
    component: Empleados,
    canActivate: [AuthGuard]
  },

  {
    path: 'asignacion-ruta',
    component: AsignacionRuta,
    canActivate: [AuthGuard]
  }

];