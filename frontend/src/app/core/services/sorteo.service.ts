import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Sorteo {
  id: number;
  nombre: string;
  fecha: string;
}

@Injectable({ providedIn: 'root' })
export class SorteoService {
  private api = '/api/sorteos';

  constructor(private http: HttpClient) {}

  listar(): Observable<Sorteo[]> {
    return this.http.get<Sorteo[]>(this.api);
  }

  obtenerPorId(id: number): Observable<Sorteo> {
    return this.http.get<Sorteo>(`${this.api}/${id}`);
  }

  crear(data: { nombre: string; fecha: string }) {
    return this.http.post(this.api, data);
  }

}
