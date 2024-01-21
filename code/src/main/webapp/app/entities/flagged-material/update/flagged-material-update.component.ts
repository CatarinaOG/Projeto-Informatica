import { Component, OnInit, inject, TemplateRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { FlaggedMaterialFormService, FlaggedMaterialFormGroup } from './flagged-material-form.service';
import { IFlaggedMaterial } from '../flagged-material.model';
import { FlaggedMaterialService } from '../service/flagged-material.service';
import { ABCClassification } from 'app/entities/enumerations/abc-classification.model';
import dayjs, { Dayjs } from 'dayjs';

/**
 * Flagged Material Update Page - Change Flag Information
 */
@Component({
  selector: 'jhi-flagged-material-update',
  templateUrl: './flagged-material-update.component.html',
})
export class FlaggedMaterialUpdateComponent implements OnInit {
  /**
   * Property created by JHipster
   */
  isSaving = false;

  /**
   * Flagged Material to be updated
   * @type {IFlaggedMaterial}
   */
  flaggedMaterial: IFlaggedMaterial | null = null;

  /**
   * ABC Classification Options
   * @type {string[]}
   */
  aBCClassificationValues = Object.keys(ABCClassification);

  /**
   * Variable that checks if the flag date is valid
   * @type {boolean}
   */
  flagError = false;

  /**
   * Date of the current day
   * @type {Date}
   */
  current = new Date();

  /**
   * Modal close result
   * @type {string}
   */
  closeResult = '';

  /**
   * Minimum date to input in date picker
   * @type {dayjs}
   */
  minDate = {
	  year: this.current.getFullYear(),
	  month: this.current.getMonth() + 1,
	  day: this.current.getDate()
	};

  /**
   * Variable that sets the status of the Save button to Enabled vs Diasabled
   */
  buttonStatus = false;

  /**
   * Property created by JHipster
   */
  editForm: FlaggedMaterialFormGroup = this.flaggedMaterialFormService.createFlaggedMaterialFormGroup();

  /**
   * Modal Service to open the Modal
   */
  private modalService = inject(NgbModal);

  /**
   * Constrcutor for the jhi-flagged-material-update component
   * @constructor
   * @param flaggedMaterialService 
   * @param flaggedMaterialFormService 
   * @param activatedRoute 
   */
  constructor(
    protected flaggedMaterialService: FlaggedMaterialService,
    protected flaggedMaterialFormService: FlaggedMaterialFormService,
    protected activatedRoute: ActivatedRoute
  ) {}


  /**
   * Function created by JHipster
   */
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

  /**
   * Function that is activated every time the information in the update form is changed.
   * The main goal is to determine if the date chosen is valid
   */
  onFormChange(): void {
    const formValue = this.editForm.value;
    this.saveButtonStatus(formValue.flagMaterial, formValue.flagExpirationDate)
  }

  /**
   * Navigates the user to the previous page (Flagged Materials Page)
   */
  previousState(): void {
    window.history.back();
  }

  /**
   * Function that determines the visibility of the date picker.
   * If the user deactivates the flag, it disappears.
   * If the user activates the flag, it becomes visible.
   * @returns CSS class that hides or shows the input 
   */
  inputHideShow() : string {
		if (!this.editForm.get('flagMaterial')?.value){
      return "tableHide"
    } 
		else{
      return ""
    } 
	}

  
  /**
   * Function that determines the visibility of the Save button.
   * If the flag is set as true and the date is invalid, it becomes disabled and a error message appears.
   * Else it becomes enabled.
   * @param flag Flag status
   * @param date Date picked by the user
   */
  saveButtonStatus(flag: boolean | null | undefined, date: Dayjs | null | undefined) : void { 
    this.flagError = false;
    if(flag === true && (date === null || date === undefined)) {
      this.flagError = true;
      this.buttonStatus= true;
    }
    else {this.buttonStatus = false};
  }

  /**
   * Main function of this Component. It receives the form content and submits it to the data base.
   * If the user chooses to disable the flag, a modal pop ups, forcing the user to confirm their option.
   * @param content Confirmation Modal
   */
  save(content: TemplateRef<any>): void {
    this.isSaving = true;
    const flaggedMaterial = this.flaggedMaterialFormService.getFlaggedMaterial(this.editForm);
    if (flaggedMaterial.id !== null) {
      if(flaggedMaterial.flagMaterial === false){
        this.open(content, flaggedMaterial)
      }
      else {this.subscribeToSaveResponse(this.flaggedMaterialService.updateFlagged(flaggedMaterial))};
    } else {
      this.subscribeToSaveResponse(this.flaggedMaterialService.create(flaggedMaterial));
    }
  }

  /**
   * Function that opens the Modal
   * @param content Confirmation Modal 
   * @param flaggedMaterial Flagged Material to Submit
   */
  open(content: TemplateRef<any>, flaggedMaterial: IFlaggedMaterial): void {
    this.modalService.open(content, { ariaLabelledBy: 'modal-basic-title' }).result.then(
      (result: string) => {
        this.closeResult = `Closed with: ${result}`;
        if (result === "Proceed"){
          if(this.flaggedMaterial){
            this.subscribeToSaveResponse(this.flaggedMaterialService.updateFlagged(flaggedMaterial))
          }
        }  
      },
    );

	}

  /**
   * Function created by JHipster
   */
  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFlaggedMaterial>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  /**
   * Function created by JHipster
   */
  protected onSaveSuccess(): void {
    this.previousState();
  }

  /**
   * Function created by JHipster
   */
  protected onSaveError(): void {
    // Api for inheritance.
  }

  /**
   * Function created by JHipster
   */
  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  /**
   * Function created by JHipster
   */
  protected updateForm(flaggedMaterial: IFlaggedMaterial): void {
    this.flaggedMaterial = flaggedMaterial;
    this.flaggedMaterialFormService.resetForm(this.editForm, flaggedMaterial);
  }
}
