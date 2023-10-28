import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDiabo } from '../diabo.model';

@Component({
  selector: 'jhi-diabo-detail',
  templateUrl: './diabo-detail.component.html',
})
export class DiaboDetailComponent implements OnInit {
  diabo: IDiabo | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ diabo }) => {
      this.diabo = diabo;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
