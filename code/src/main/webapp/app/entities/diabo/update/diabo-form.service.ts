import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IDiabo, NewDiabo } from '../diabo.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IDiabo for edit and NewDiaboFormGroupInput for create.
 */
type DiaboFormGroupInput = IDiabo | PartialWithRequiredKeyOf<NewDiabo>;

type DiaboFormDefaults = Pick<NewDiabo, 'id' | 'selectEntriesForChangeInSap' | 'flagMaterialAsSpecialCase'>;

type DiaboFormGroupContent = {
  id: FormControl<IDiabo['id'] | NewDiabo['id']>;
  material: FormControl<IDiabo['material']>;
  materialDescription: FormControl<IDiabo['materialDescription']>;
  abcClassification: FormControl<IDiabo['abcClassification']>;
  avgSupplierDelayLast90Days: FormControl<IDiabo['avgSupplierDelayLast90Days']>;
  maxSupplierDelayLast90Days: FormControl<IDiabo['maxSupplierDelayLast90Days']>;
  serviceLevel: FormControl<IDiabo['serviceLevel']>;
  currentSapSafetyStock: FormControl<IDiabo['currentSapSafetyStock']>;
  proposedSafetyStock: FormControl<IDiabo['proposedSafetyStock']>;
  deltaSafetyStock: FormControl<IDiabo['deltaSafetyStock']>;
  currentSapSafetyTime: FormControl<IDiabo['currentSapSafetyTime']>;
  proposedSafetyTime: FormControl<IDiabo['proposedSafetyTime']>;
  deltaSafetyTime: FormControl<IDiabo['deltaSafetyTime']>;
  openSapMd04: FormControl<IDiabo['openSapMd04']>;
  currentInventoryValue: FormControl<IDiabo['currentInventoryValue']>;
  averageInventoryEffectAfterChange: FormControl<IDiabo['averageInventoryEffectAfterChange']>;
  newSapSafetyStock: FormControl<IDiabo['newSapSafetyStock']>;
  newSapSafetyTime: FormControl<IDiabo['newSapSafetyTime']>;
  selectEntriesForChangeInSap: FormControl<IDiabo['selectEntriesForChangeInSap']>;
  flagMaterialAsSpecialCase: FormControl<IDiabo['flagMaterialAsSpecialCase']>;
  commentary: FormControl<IDiabo['commentary']>;
};

export type DiaboFormGroup = FormGroup<DiaboFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class DiaboFormService {
  createDiaboFormGroup(diabo: DiaboFormGroupInput = { id: null }): DiaboFormGroup {
    const diaboRawValue = {
      ...this.getFormDefaults(),
      ...diabo,
    };
    return new FormGroup<DiaboFormGroupContent>({
      id: new FormControl(
        { value: diaboRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      material: new FormControl(diaboRawValue.material),
      materialDescription: new FormControl(diaboRawValue.materialDescription),
      abcClassification: new FormControl(diaboRawValue.abcClassification),
      avgSupplierDelayLast90Days: new FormControl(diaboRawValue.avgSupplierDelayLast90Days),
      maxSupplierDelayLast90Days: new FormControl(diaboRawValue.maxSupplierDelayLast90Days),
      serviceLevel: new FormControl(diaboRawValue.serviceLevel),
      currentSapSafetyStock: new FormControl(diaboRawValue.currentSapSafetyStock),
      proposedSafetyStock: new FormControl(diaboRawValue.proposedSafetyStock),
      deltaSafetyStock: new FormControl(diaboRawValue.deltaSafetyStock),
      currentSapSafetyTime: new FormControl(diaboRawValue.currentSapSafetyTime),
      proposedSafetyTime: new FormControl(diaboRawValue.proposedSafetyTime),
      deltaSafetyTime: new FormControl(diaboRawValue.deltaSafetyTime),
      openSapMd04: new FormControl(diaboRawValue.openSapMd04),
      currentInventoryValue: new FormControl(diaboRawValue.currentInventoryValue),
      averageInventoryEffectAfterChange: new FormControl(diaboRawValue.averageInventoryEffectAfterChange),
      newSapSafetyStock: new FormControl(diaboRawValue.newSapSafetyStock),
      newSapSafetyTime: new FormControl(diaboRawValue.newSapSafetyTime),
      selectEntriesForChangeInSap: new FormControl(diaboRawValue.selectEntriesForChangeInSap),
      flagMaterialAsSpecialCase: new FormControl(diaboRawValue.flagMaterialAsSpecialCase),
      commentary: new FormControl(diaboRawValue.commentary),
    });
  }

  getDiabo(form: DiaboFormGroup): IDiabo | NewDiabo {
    return form.getRawValue() as IDiabo | NewDiabo;
  }

  resetForm(form: DiaboFormGroup, diabo: DiaboFormGroupInput): void {
    const diaboRawValue = { ...this.getFormDefaults(), ...diabo };
    form.reset(
      {
        ...diaboRawValue,
        id: { value: diaboRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): DiaboFormDefaults {
    return {
      id: null,
      selectEntriesForChangeInSap: false,
      flagMaterialAsSpecialCase: false,
    };
  }
}
