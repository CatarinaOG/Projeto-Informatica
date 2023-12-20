import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IFlaggedMaterial, NewFlaggedMaterial } from '../flagged-material.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IFlaggedMaterial for edit and NewFlaggedMaterialFormGroupInput for create.
 */
type FlaggedMaterialFormGroupInput = IFlaggedMaterial | PartialWithRequiredKeyOf<NewFlaggedMaterial>;

type FlaggedMaterialFormDefaults = Pick<NewFlaggedMaterial, 'id' | 'flagMaterial' | 'toSaveUpdates'>;

type FlaggedMaterialFormGroupContent = {
  id: FormControl<IFlaggedMaterial['id'] | NewFlaggedMaterial['id']>;
  material: FormControl<IFlaggedMaterial['material']>;
  description: FormControl<IFlaggedMaterial['description']>;
  abcClassification: FormControl<IFlaggedMaterial['abcClassification']>;
  avgSupplierDelay: FormControl<IFlaggedMaterial['avgSupplierDelay']>;
  maxSupplierDelay: FormControl<IFlaggedMaterial['maxSupplierDelay']>;
  serviceLevel: FormControl<IFlaggedMaterial['serviceLevel']>;
  plant: FormControl<IFlaggedMaterial['plant']>;
  mrpController: FormControl<IFlaggedMaterial['mrpController']>;
  currSAPSafetyStock: FormControl<IFlaggedMaterial['currSAPSafetyStock']>;
  proposedSST: FormControl<IFlaggedMaterial['proposedSST']>;
  deltaSST: FormControl<IFlaggedMaterial['deltaSST']>;
  currentSAPSafeTime: FormControl<IFlaggedMaterial['currentSAPSafeTime']>;
  proposedST: FormControl<IFlaggedMaterial['proposedST']>;
  deltaST: FormControl<IFlaggedMaterial['deltaST']>;
  openSAPmd04: FormControl<IFlaggedMaterial['openSAPmd04']>;
  currentInventoryValue: FormControl<IFlaggedMaterial['currentInventoryValue']>;
  unitCost: FormControl<IFlaggedMaterial['unitCost']>;
  avgDemand: FormControl<IFlaggedMaterial['avgDemand']>;
  avgInventoryEffectAfterChange: FormControl<IFlaggedMaterial['avgInventoryEffectAfterChange']>;
  flagMaterial: FormControl<IFlaggedMaterial['flagMaterial']>;
  flagExpirationDate: FormControl<IFlaggedMaterial['flagExpirationDate']>;
  valueOfUpdatedSS: FormControl<IFlaggedMaterial['valueOfUpdatedSS']>;
  valueOfUpdatedST: FormControl<IFlaggedMaterial['valueOfUpdatedST']>;
  dateOfUpdatedSS: FormControl<IFlaggedMaterial['dateOfUpdatedSS']>;
  dateOfUpdatedST: FormControl<IFlaggedMaterial['dateOfUpdatedST']>;
  toSaveUpdates: FormControl<IFlaggedMaterial['toSaveUpdates']>;
  currency: FormControl<IFlaggedMaterial['currency']>;
};

export type FlaggedMaterialFormGroup = FormGroup<FlaggedMaterialFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class FlaggedMaterialFormService {
  createFlaggedMaterialFormGroup(flaggedMaterial: FlaggedMaterialFormGroupInput = { id: null }): FlaggedMaterialFormGroup {
    const flaggedMaterialRawValue = {
      ...this.getFormDefaults(),
      ...flaggedMaterial,
    };
    return new FormGroup<FlaggedMaterialFormGroupContent>({
      id: new FormControl(
        { value: flaggedMaterialRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      material: new FormControl(flaggedMaterialRawValue.material),
      description: new FormControl(flaggedMaterialRawValue.description),
      abcClassification: new FormControl(flaggedMaterialRawValue.abcClassification),
      avgSupplierDelay: new FormControl(flaggedMaterialRawValue.avgSupplierDelay),
      maxSupplierDelay: new FormControl(flaggedMaterialRawValue.maxSupplierDelay),
      serviceLevel: new FormControl(flaggedMaterialRawValue.serviceLevel),
      plant: new FormControl(flaggedMaterialRawValue.plant),
      mrpController: new FormControl(flaggedMaterialRawValue.mrpController),
      currSAPSafetyStock: new FormControl(flaggedMaterialRawValue.currSAPSafetyStock),
      proposedSST: new FormControl(flaggedMaterialRawValue.proposedSST),
      deltaSST: new FormControl(flaggedMaterialRawValue.deltaSST),
      currentSAPSafeTime: new FormControl(flaggedMaterialRawValue.currentSAPSafeTime),
      proposedST: new FormControl(flaggedMaterialRawValue.proposedST),
      deltaST: new FormControl(flaggedMaterialRawValue.deltaST),
      openSAPmd04: new FormControl(flaggedMaterialRawValue.openSAPmd04),
      currentInventoryValue: new FormControl(flaggedMaterialRawValue.currentInventoryValue),
      unitCost: new FormControl(flaggedMaterialRawValue.unitCost),
      avgDemand: new FormControl(flaggedMaterialRawValue.avgDemand),
      avgInventoryEffectAfterChange: new FormControl(flaggedMaterialRawValue.avgInventoryEffectAfterChange),
      flagMaterial: new FormControl(flaggedMaterialRawValue.flagMaterial),
      flagExpirationDate: new FormControl(flaggedMaterialRawValue.flagExpirationDate),
      valueOfUpdatedSS: new FormControl(flaggedMaterialRawValue.valueOfUpdatedSS),
      valueOfUpdatedST: new FormControl(flaggedMaterialRawValue.valueOfUpdatedST),
      dateOfUpdatedSS: new FormControl(flaggedMaterialRawValue.dateOfUpdatedSS),
      dateOfUpdatedST: new FormControl(flaggedMaterialRawValue.dateOfUpdatedST),
      toSaveUpdates: new FormControl(flaggedMaterialRawValue.toSaveUpdates),
      currency: new FormControl(flaggedMaterialRawValue.currency),
    });
  }

  getFlaggedMaterial(form: FlaggedMaterialFormGroup): IFlaggedMaterial | NewFlaggedMaterial {
    return form.getRawValue() as IFlaggedMaterial | NewFlaggedMaterial;
  }

  resetForm(form: FlaggedMaterialFormGroup, flaggedMaterial: FlaggedMaterialFormGroupInput): void {
    const flaggedMaterialRawValue = { ...this.getFormDefaults(), ...flaggedMaterial };
    form.reset(
      {
        ...flaggedMaterialRawValue,
        id: { value: flaggedMaterialRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): FlaggedMaterialFormDefaults {
    return {
      id: null,
      flagMaterial: false,
      toSaveUpdates: false,
    };
  }
}
