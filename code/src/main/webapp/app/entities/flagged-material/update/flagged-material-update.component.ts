import { Component, OnInit, inject, TemplateRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';
import {  NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { FlaggedMaterialFormService, FlaggedMaterialFormGroup } from './flagged-material-form.service';
import { IFlaggedMaterial } from '../flagged-material.model';
import { FlaggedMaterialService } from '../service/flagged-material.service';
import { ABCClassification } from 'app/entities/enumerations/abc-classification.model';
import { Coin } from 'app/entities/enumerations/coin.model';
import { Dayjs } from 'dayjs';

@Component({
  selector: 'jhi-flagged-material-update',
  templateUrl: './flagged-material-update.component.html',
})
export class FlaggedMaterialUpdateComponent implements OnInit {
  isSaving = false;
  flaggedMaterial: IFlaggedMaterial | null = null;
  aBCClassificationValues = Object.keys(ABCClassification);
  coinValues = Object.keys(Coin);
	
  flagError = false;

  current = new Date();
  closeResult = '';
  minDate = {
	  year: this.current.getFullYear(),
	  month: this.current.getMonth() + 1,
	  day: this.current.getDate()
	};
  buttonStatus = false;

  editForm: FlaggedMaterialFormGroup = this.flaggedMaterialFormService.createFlaggedMaterialFormGroup();

  private modalService = inject(NgbModal);

 
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
        this.onFormChange();
        // Subscribe to form value changes
        this.editForm.valueChanges.subscribe(() => {
          this.onFormChange();
        });
      }
    });
  }

  onFormChange(): void {
    const formValue = this.editForm.value;
    this.saveButtonStatus(formValue.flagMaterial, formValue.flagExpirationDate)
  }

  previousState(): void {
    window.history.back();
  }

  inputHideShow() : string {
		if (!this.editForm.get('flagMaterial')?.value){
      return "tableHide"
    } 
		else{
      return ""
    } 
	}



  saveButtonStatus(flag: boolean | null | undefined, date: Dayjs | null | undefined) : void { 
    this.flagError = false;
    if(flag === true && (date === null || date === undefined)) {
      this.flagError = true;
      this.buttonStatus= true;
    }
    else {this.buttonStatus = false};
  }


  save(content: TemplateRef<any>): void {
    this.isSaving = true;
    const flaggedMaterial = this.flaggedMaterialFormService.getFlaggedMaterial(this.editForm);
    if (flaggedMaterial.id !== null) {
      if(flaggedMaterial.flagMaterial === false){
        this.open(content)
        
      }
      else {this.subscribeToSaveResponse(this.flaggedMaterialService.update(flaggedMaterial))};
    } else {
      this.subscribeToSaveResponse(this.flaggedMaterialService.create(flaggedMaterial));
    }
  }

  open(content: TemplateRef<any>): void {
    this.modalService.open(content, { ariaLabelledBy: 'modal-basic-title' }).result.then(
      (result) => {
        this.closeResult = `Closed with: ${result}`;
        if (result === "Proceed"){
          if(this.flaggedMaterial){
            this.flaggedMaterialService.delete(this.flaggedMaterial?.id).subscribe({
              next: () => this.onSaveSuccess(),
              error: () => this.onSaveError(),
            });
          }
        }  
      }, 
      // (reason) => {
      //   // this.closeResult = `Dismissed ${this.getDismissReason(reason)}`;
      // },
    );

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
