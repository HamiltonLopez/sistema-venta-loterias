import { Component, OnInit, OnDestroy, ChangeDetectorRef, inject } from '@angular/core';
import { ActivatedRoute, Router, NavigationEnd } from '@angular/router';
import { filter, Subscription } from 'rxjs';
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
export class SorteoDetalleComponent implements OnInit, OnDestroy {

  sorteo: Sorteo | undefined;
  form!: FormGroup;
  formGenerar!: FormGroup;
  billetesExistentes: any[] = [];
  clientes: Cliente[] = [];
  mostrarDialogoVenta: boolean = false;
  billeteSeleccionado: any = null;
  clienteSeleccionado: number | null = null;
  private routerSubscription?: Subscription;
  private routeSubscription?: Subscription;
  private cdr = inject(ChangeDetectorRef);

  constructor(
    private route: ActivatedRoute,
    private sorteoService: SorteoService,
    private billeteService: BilleteService,
    private clienteService: ClienteService,
    private fb: FormBuilder,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.form = this.fb.group({
      billetes: this.fb.array([
        this.fb.group({ numero: ['', Validators.required], precio: ['', Validators.required] })
      ])
    });

    this.formGenerar = this.fb.group({
      numeroCifras: [3, [Validators.required, Validators.min(1), Validators.max(6)]],
      precio: ['', [Validators.required, Validators.min(0.01)]]
    });

    this.routeSubscription = this.route.paramMap.subscribe(params => {
      const id = Number(params.get('id'));
      if (id) {
        this.cargarSorteo(id);
        this.cargarBilletes(id);
      }
    });

    this.cargarClientes();
  }

  ngOnDestroy(): void {
    if (this.routerSubscription) {
      this.routerSubscription.unsubscribe();
    }
    if (this.routeSubscription) {
      this.routeSubscription.unsubscribe();
    }
  }

  cargarSorteo(id: number): void {
    this.sorteoService.obtenerPorId(id).subscribe((data) => {
      this.sorteo = data;
      this.cdr.detectChanges();
    });
  }

  cargarBilletes(id: number): void {
    this.billeteService.listarPorSorteo(id).subscribe((data) => {
      this.billetesExistentes = data;
      this.cdr.detectChanges();
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
      this.cdr.detectChanges();
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
    this.billeteService.crearBilletes(id, this.form.value.billetes).subscribe({
      next: (response: any) => {
        const mensaje = typeof response === 'string' ? response : 'Billetes creados correctamente';
        alert(mensaje);
        this.cargarBilletes(id);
        this.form.reset();
        this.billetes.clear();
        this.billetes.push(this.fb.group({ numero: ['', Validators.required], precio: ['', Validators.required] }));
      },
      error: (error) => {
        let mensaje = 'Error al crear billetes';
        if (error.error) {
          mensaje = typeof error.error === 'string' ? error.error : (error.error.message || error.error);
        }
        alert('Error: ' + mensaje);
      }
    });
  }

  generarBilletes() {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    if (this.formGenerar.invalid) return;
    
    const numeroCifras = this.formGenerar.value.numeroCifras;
    const precio = this.formGenerar.value.precio;
    
    const totalBilletes = Math.pow(10, numeroCifras);
    if (!confirm(`¿Está seguro de generar ${totalBilletes} billetes?`)) {
      return;
    }

    this.billeteService.generarBilletes(id, numeroCifras, precio).subscribe({
      next: (response: any) => {
        const mensaje = typeof response === 'string' ? response : 'Billetes generados correctamente';
        alert(mensaje);
        this.cargarBilletes(id);
        this.formGenerar.reset();
        this.formGenerar.patchValue({ numeroCifras: 3 });
      },
      error: (error) => {
        let mensaje = 'Error al generar billetes';
        if (error.error) {
          mensaje = typeof error.error === 'string' ? error.error : (error.error.message || error.error);
        }
        alert('Error: ' + mensaje);
      }
    });
  }

}
