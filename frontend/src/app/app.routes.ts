import { Routes } from '@angular/router';
import { SorteosListComponent } from './features/sorteos/sorteo-list/sorteos-list';
import { SorteoCreateComponent } from './features/sorteos/sorteo-create/sorteo-create';
export const routes: Routes = [
  { path: '', redirectTo: 'sorteos', pathMatch: 'full' },
  { path: 'sorteos', component: SorteosListComponent },
  { path: 'sorteos/crear', component: SorteoCreateComponent }
];
