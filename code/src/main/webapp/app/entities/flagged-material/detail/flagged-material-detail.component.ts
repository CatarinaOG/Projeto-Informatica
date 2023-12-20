import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFlaggedMaterial } from '../flagged-material.model';

@Component({
  selector: 'jhi-flagged-material-detail',
  templateUrl: './flagged-material-detail.component.html',
})
export class FlaggedMaterialDetailComponent implements OnInit {
  flaggedMaterial: IFlaggedMaterial | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ flaggedMaterial }) => {
      this.flaggedMaterial = flaggedMaterial;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
