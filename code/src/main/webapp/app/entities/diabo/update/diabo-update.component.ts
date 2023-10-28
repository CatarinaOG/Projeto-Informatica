import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { DiaboFormService, DiaboFormGroup } from './diabo-form.service';
import { IDiabo } from '../diabo.model';
import { DiaboService } from '../service/diabo.service';
import { ABCClassification } from 'app/entities/enumerations/abc-classification.model';

@Component({
  selector: 'jhi-diabo-update',
  templateUrl: './diabo-update.component.html',
})
export class DiaboUpdateComponent implements OnInit {
  isSaving = false;
  diabo: IDiabo | null = null;
  aBCClassificationValues = Object.keys(ABCClassification);

  editForm: DiaboFormGroup = this.diaboFormService.createDiaboFormGroup();

  constructor(
    protected diaboService: DiaboService,
    protected diaboFormService: DiaboFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ diabo }) => {
      this.diabo = diabo;
      if (diabo) {
        this.updateForm(diabo);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const diabo = this.diaboFormService.getDiabo(this.editForm);
    if (diabo.id !== null) {
      this.subscribeToSaveResponse(this.diaboService.update(diabo));
    } else {
      this.subscribeToSaveResponse(this.diaboService.create(diabo));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDiabo>>): void {
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

  protected updateForm(diabo: IDiabo): void {
    this.diabo = diabo;
    this.diaboFormService.resetForm(this.editForm, diabo);
  }
}
