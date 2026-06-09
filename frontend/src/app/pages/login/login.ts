import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './login.html',
  styleUrl: './login.css'
})
export class Login {

  usuario: string = '';
  password: string = '';
  mensaje: string = '';

  constructor(private router: Router) {}

  iniciarSesion() {

    console.log(this.usuario);
    console.log(this.password);

    if (this.usuario === 'admin' && this.password === '1234') {

      this.router.navigate(['/inicio']);

    } else {

      this.mensaje = 'Usuario o contraseña incorrectos';

    }

  }

}