import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { FlaggedMaterialFormService, FlaggedMaterialFormGroup } from './flagged-material-form.service';
import { IFlaggedMaterial } from '../flagged-material.model';
import { FlaggedMaterialService } from '../service/flagged-material.service';
import { ABCClassification } from 'app/entities/enumerations/abc-classification.model';

@Component({
  selector: 'jhi-flagged-material-update',
  templateUrl: './flagged-material-update.component.html',
})
export class FlaggedMaterialUpdateComponent implements OnInit {
  isSaving = false;
  flaggedMaterial: IFlaggedMaterial | null = null;
  aBCClassificationValues = Object.keys(ABCClassification);

  editForm: FlaggedMaterialFormGroup = this.flaggedMaterialFormService.createFlaggedMaterialFormGroup();

  constructor(
    protected flaggedMaterialService: FlaggedMaterialService,
    protected flaggedMaterialFormService: FlaggedMaterialFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ flaggedMaterial }) => {
      this.flaggedMaterial = flaggedMaterial;
      if (flaggedMaterial) {
        this.updateForm(flaggedMaterial);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const flaggedMaterial = this.flaggedMaterialFormService.getFlaggedMaterial(this.editForm);
    if (flaggedMaterial.id !== null) {
      this.subscribeToSaveResponse(this.flaggedMaterialService.update(flaggedMaterial));
    } else {
      this.subscribeToSaveResponse(this.flaggedMaterialService.create(flaggedMaterial));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFlaggedMaterial>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(flaggedMaterial: IFlaggedMaterial): void {
    this.flaggedMaterial = flaggedMaterial;
    this.flaggedMaterialFormService.resetForm(this.editForm, flaggedMaterial);
  }
}
