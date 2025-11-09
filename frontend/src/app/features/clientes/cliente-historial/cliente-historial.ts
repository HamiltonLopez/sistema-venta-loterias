import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ClienteService, Cliente } from '../../../core/services/cliente.service';
import { BilleteService } from '../../../core/services/billete.service';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatSelectModule } from '@angular/material/select';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { RouterLink } from '@angular/router';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-cliente-historial',
  standalone: true,
  imports: [
    CommonModule,
    MatCardModule,
    MatButtonModule,
    MatSelectModule,
    MatFormFieldModule,
    MatIconModule,
    RouterLink,
    FormsModule
  ],
  templateUrl: './cliente-historial.html',
  styleUrls: ['./cliente-historial.scss']
})
export class ClienteHistorialComponent implements OnInit {

  clientes: Cliente[] = [];
  clienteSeleccionado: number | null = null;
  billetes: any[] = [];

  constructor(
    private clienteService: ClienteService,
    private billeteService: BilleteService
  ) {}

  ngOnInit(): void {
    this.cargarClientes();
  }

  cargarClientes(): void {
    this.clienteService.listar().subscribe((data) => {
      this.clientes = data;
    });
  }

  cargarBilletes(): void {
    if (!this.clienteSeleccionado) {
      this.billetes = [];
      return;
    }

    this.billeteService.listarPorCliente(this.clienteSeleccionado).subscribe((data) => {
      this.billetes = data;
    });
  }

  onClienteChange(): void {
    this.cargarBilletes();
  }
}

