import { Component, OnInit, OnDestroy, ChangeDetectorRef } from '@angular/core';
import { SorteoService, Sorteo } from '../../../core/services/sorteo.service';
import { NgFor } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { RouterLink, Router, NavigationEnd, ActivatedRoute } from '@angular/router';
import { MatIconModule } from '@angular/material/icon';
import { filter, Subscription } from 'rxjs';

@Component({
  selector: 'app-sorteos-list',
  standalone: true,
  imports: [NgFor, MatCardModule, MatButtonModule, RouterLink, MatIconModule],
  templateUrl: './sorteos-list.html',
  styleUrls: ['./sorteos-list.scss']
})
export class SorteosListComponent implements OnInit, OnDestroy {

  sorteos: Sorteo[] = [];
  private routerSubscription?: Subscription;
  private recargaSubscription?: Subscription;

  constructor(
    private sorteoService: SorteoService,
    private router: Router,
    private route: ActivatedRoute,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.cargarSorteos();

    this.routerSubscription = this.router.events
      .pipe(filter(event => event instanceof NavigationEnd))
      .subscribe((event: NavigationEnd) => {
        const url = event.urlAfterRedirects || event.url;
        if (url === '/sorteos') {
          this.cargarSorteos();
        }
      });

    this.recargaSubscription = this.sorteoService.onRecargarSorteos.subscribe(() => {
      this.cargarSorteos();
    });
  }

  ngOnDestroy(): void {
    if (this.routerSubscription) {
      this.routerSubscription.unsubscribe();
    }
    if (this.recargaSubscription) {
      this.recargaSubscription.unsubscribe();
    }
  }

  cargarSorteos(): void {
    this.sorteoService.listar().subscribe(data => {
      this.sorteos = data;
      this.cdr.detectChanges();
    });
  }
}

