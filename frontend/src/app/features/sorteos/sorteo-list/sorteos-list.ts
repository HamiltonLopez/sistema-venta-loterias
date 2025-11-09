import { Component, OnInit } from '@angular/core';
import { SorteoService, Sorteo } from '../../../core/services/sorteo.service';
import { NgFor } from '@angular/common';
import { MatCardModule } from '@angular/material/card';

@Component({
  selector: 'app-sorteos-list',
  standalone: true,
  imports: [NgFor, MatCardModule],
  templateUrl: './sorteos-list.html',
  styleUrls: ['./sorteos-list.scss']
})
export class SorteosListComponent implements OnInit {

  sorteos: Sorteo[] = [];

  constructor(private sorteoService: SorteoService) {}

  ngOnInit(): void {
    this.sorteoService.listar().subscribe(data => {
      this.sorteos = data;
    });
  }
}

