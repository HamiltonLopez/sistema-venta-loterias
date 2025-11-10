import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Cliente {
  id: number;
  nombre: string;
  correo: string;
}

@Injectable({ providedIn: 'root' })
export class ClienteService {
  private api = '/api/clientes';

  constructor(private http: HttpClient) {}

  listar(): Observable<Cliente[]> {
    return this.http.get<Cliente[]>(this.api);
  }

  crear(data: { nombre: string; correo: string }): Observable<Cliente> {
    return this.http.post<Cliente>(this.api, data);
  }
}

