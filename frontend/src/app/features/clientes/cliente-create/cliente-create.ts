import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ClienteService } from '../../../core/services/cliente.service';
import { Router } from '@angular/router';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-cliente-create',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule
  ],
  templateUrl: './cliente-create.html',
  styleUrls: ['./cliente-create.scss']
})
export class ClienteCreateComponent {

  form!: FormGroup;

  constructor(
    private fb: FormBuilder,
    private clienteService: ClienteService,
    private router: Router
  ) {
    this.form = this.fb.group({
      nombre: ['', Validators.required],
      correo: ['', [Validators.required, Validators.email]]
    });
  }

  guardar() {
    if (this.form.invalid) return;

    this.clienteService.crear(this.form.value).subscribe(() => {
      alert('Cliente registrado correctamente');
      this.router.navigate(['/clientes']);
    }, (error) => {
      alert('Error al registrar cliente: ' + (error.error?.message || 'Error desconocido'));
    });
  }
}

