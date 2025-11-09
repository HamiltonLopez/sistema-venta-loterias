import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormArray, FormGroup, ReactiveFormsModule, FormsModule, Validators } from '@angular/forms';
import { SorteoService, Sorteo } from '../../../core/services/sorteo.service';
import { BilleteService } from '../../../core/services/billete.service';
import { ClienteService, Cliente } from '../../../core/services/cliente.service';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatDialogModule } from '@angular/material/dialog';
import { Router } from '@angular/router';
@Component({
  selector: 'app-sorteo-detalle',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    FormsModule,
    MatCardModule,
    MatButtonModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatDialogModule
  ],
  templateUrl: './sorteo-detalle.html',
  styleUrls: ['./sorteo-detalle.scss']
})
export class SorteoDetalleComponent implements OnInit {

  sorteo: Sorteo | undefined;
  form!: FormGroup;
  billetesExistentes: any[] = [];
  clientes: Cliente[] = [];
  mostrarDialogoVenta: boolean = false;
  billeteSeleccionado: any = null;
  clienteSeleccionado: number | null = null;

  constructor(
    private route: ActivatedRoute,
    private sorteoService: SorteoService,
    private billeteService: BilleteService,
    private clienteService: ClienteService,
    private fb: FormBuilder,
    private router: Router 
  ) {}

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.form = this.fb.group({
      billetes: this.fb.array([
        this.fb.group({ numero: ['', Validators.required], precio: ['', Validators.required] })
      ])
    });

    this.cargarSorteo(id);
    this.cargarBilletes(id);
    this.cargarClientes();
  }

  cargarSorteo(id: number): void {
    this.sorteoService.obtenerPorId(id).subscribe((data) => {
      this.sorteo = data;
    });
  }

  cargarBilletes(id: number): void {
    this.billeteService.listarPorSorteo(id).subscribe((data) => {
      this.billetesExistentes = data;
    });
  }

  get billetes(): FormArray {
    return this.form.get('billetes') as FormArray;
  }

  agregarFila() {
    this.billetes.push(this.fb.group({ numero: ['', Validators.required], precio: ['', Validators.required] }));
  }

  eliminarFila(i: number) {
    this.billetes.removeAt(i);
  }

  cargarClientes(): void {
    this.clienteService.listar().subscribe((data) => {
      this.clientes = data;
    });
  }

  abrirDialogoVenta(billete: any): void {
    this.billeteSeleccionado = billete;
    this.mostrarDialogoVenta = true;
    this.clienteSeleccionado = null;
  }

  cerrarDialogoVenta(): void {
    this.mostrarDialogoVenta = false;
    this.billeteSeleccionado = null;
    this.clienteSeleccionado = null;
  }

  venderBillete(): void {
    if (!this.billeteSeleccionado || !this.clienteSeleccionado) {
      alert('Por favor seleccione un cliente');
      return;
    }

    this.billeteService.vender(this.billeteSeleccionado.id, this.clienteSeleccionado).subscribe(() => {
      alert('Billete vendido correctamente');
      const id = Number(this.route.snapshot.paramMap.get('id'));
      this.cargarBilletes(id);
      this.cerrarDialogoVenta();
    }, (error) => {
      alert('Error al vender el billete: ' + (error.error?.message || 'Error desconocido'));
    });
  }

  guardar() {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    if (this.form.invalid) return;
    this.billeteService.crearBilletes(id, this.form.value.billetes).subscribe(() => {
      alert('Billetes creados correctamente');
      this.cargarBilletes(id);
      this.form.reset();
      this.billetes.clear();
      this.billetes.push(this.fb.group({ numero: ['', Validators.required], precio: ['', Validators.required] }));
    });
  }

}
