import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { SorteoService } from '../../../core/services/sorteo.service';
import { Router } from '@angular/router';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-sorteo-create',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatDatepickerModule,
    MatNativeDateModule
  ],
  templateUrl: './sorteo-create.html',
  styleUrls: ['./sorteo-create.scss']
})
export class SorteoCreateComponent {

  form!: FormGroup;

  constructor(
    private fb: FormBuilder,
    private sorteoService: SorteoService,
    private router: Router
  ) {
    this.form = this.fb.group({
      nombre: ['', Validators.required],
      fecha: ['', Validators.required]
    });
  }

  guardar() {
    if (this.form.invalid) return;

    const data = {
      nombre: this.form.value.nombre!,
      fecha: this.form.value.fecha!.toISOString().split('T')[0]
    };

    this.sorteoService.crear(data).subscribe(() => {
      this.sorteoService.notificarRecarga();
      this.router.navigate(['/sorteos']);
    });
  }
}
