import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class BilleteService {
  private api = '/api/billetes';

  constructor(private http: HttpClient) {}

  crearBilletes(sorteoId: number, billetes: { numero: string; precio: number }[]): Observable<any> {
    return this.http.post(`/api/sorteos/${sorteoId}/billetes`, { billetes });
  }

  listarPorSorteo(sorteoId: number): Observable<any[]> {
    return this.http.get<any[]>(`/api/sorteos/${sorteoId}/billetes`);
  }

  vender(billeteId: number, clienteId: number): Observable<any> {
    return this.http.post(`/api/billetes/${billeteId}/vender`, { clienteId });
  }

  listarPorCliente(clienteId: number): Observable<any[]> {
    return this.http.get<any[]>(`/api/billetes/cliente/${clienteId}`);
  }
}
