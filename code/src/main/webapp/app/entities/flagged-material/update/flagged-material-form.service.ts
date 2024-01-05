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

  plant: FormControl<IFlaggedMaterial['plant']>;
  mrpController: FormControl<IFlaggedMaterial['mrpController']>;
  
  flagMaterial: FormControl<IFlaggedMaterial['flagMaterial']>;
  flagExpirationDate: FormControl<IFlaggedMaterial['flagExpirationDate']>;
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
      plant: new FormControl(flaggedMaterialRawValue.plant),
      mrpController: new FormControl(flaggedMaterialRawValue.mrpController),
      
      flagMaterial: new FormControl(flaggedMaterialRawValue.flagMaterial),
      flagExpirationDate: new FormControl(flaggedMaterialRawValue.flagExpirationDate),
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
