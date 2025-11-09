import { Routes } from '@angular/router';
import { SorteosListComponent } from './features/sorteos/sorteo-list/sorteos-list';
import { SorteoCreateComponent } from './features/sorteos/sorteo-create/sorteo-create';
import { SorteoDetalleComponent } from './features/sorteos/sorteo-detalle/sorteo-detalle';
import { ClienteCreateComponent } from './features/clientes/cliente-create/cliente-create';
import { ClienteHistorialComponent } from './features/clientes/cliente-historial/cliente-historial';

export const routes: Routes = [
  { path: '', redirectTo: 'sorteos', pathMatch: 'full' },
  { path: 'sorteos', component: SorteosListComponent },
  { path: 'sorteos/crear', component: SorteoCreateComponent },
  { path: 'sorteos/:id', component: SorteoDetalleComponent },
  { path: 'clientes', component: ClienteHistorialComponent },
  { path: 'clientes/crear', component: ClienteCreateComponent }
];
