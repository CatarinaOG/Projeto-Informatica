import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IMaterial, NewMaterial } from '../material.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IMaterial for edit and NewMaterialFormGroupInput for create.
 */
type MaterialFormGroupInput = IMaterial | PartialWithRequiredKeyOf<NewMaterial>;

type MaterialFormDefaults = Pick<NewMaterial, 'id' | 'flagMaterial'>;

type MaterialFormGroupContent = {
  id: FormControl<IMaterial['id'] | NewMaterial['id']>;
  material: FormControl<IMaterial['material']>;
  description: FormControl<IMaterial['description']>;
  abcClassification: FormControl<IMaterial['abcClassification']>;
  avgSupplierDelay: FormControl<IMaterial['avgSupplierDelay']>;
  maxSupplierDelay: FormControl<IMaterial['maxSupplierDelay']>;
  serviceLevel: FormControl<IMaterial['serviceLevel']>;
  currSAPSafetyStock: FormControl<IMaterial['currSAPSafetyStock']>;
  propsedSST: FormControl<IMaterial['propsedSST']>;
  deltaSST: FormControl<IMaterial['deltaSST']>;
  currentSAPSafeTime: FormControl<IMaterial['currentSAPSafeTime']>;
  proposedST: FormControl<IMaterial['proposedST']>;
  deltaST: FormControl<IMaterial['deltaST']>;
  openSAPmd04: FormControl<IMaterial['openSAPmd04']>;
  currentInventoryValue: FormControl<IMaterial['currentInventoryValue']>;
  unitCost: FormControl<IMaterial['unitCost']>;
  avgDemand: FormControl<IMaterial['avgDemand']>;
  avgInventoryEffectAfterChange: FormControl<IMaterial['avgInventoryEffectAfterChange']>;
  flagMaterial: FormControl<IMaterial['flagMaterial']>;
  comment: FormControl<IMaterial['comment']>;
};

export type MaterialFormGroup = FormGroup<MaterialFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class MaterialFormService {
  createMaterialFormGroup(material: MaterialFormGroupInput = { id: null }): MaterialFormGroup {
    const materialRawValue = {
      ...this.getFormDefaults(),
      ...material,
    };
    return new FormGroup<MaterialFormGroupContent>({
      id: new FormControl(
        { value: materialRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      material: new FormControl(materialRawValue.material),
      description: new FormControl(materialRawValue.description),
      abcClassification: new FormControl(materialRawValue.abcClassification),
      avgSupplierDelay: new FormControl(materialRawValue.avgSupplierDelay),
      maxSupplierDelay: new FormControl(materialRawValue.maxSupplierDelay),
      serviceLevel: new FormControl(materialRawValue.serviceLevel),
      currSAPSafetyStock: new FormControl(materialRawValue.currSAPSafetyStock),
      propsedSST: new FormControl(materialRawValue.propsedSST),
      deltaSST: new FormControl(materialRawValue.deltaSST),
      currentSAPSafeTime: new FormControl(materialRawValue.currentSAPSafeTime),
      proposedST: new FormControl(materialRawValue.proposedST),
      deltaST: new FormControl(materialRawValue.deltaST),
      openSAPmd04: new FormControl(materialRawValue.openSAPmd04),
      currentInventoryValue: new FormControl(materialRawValue.currentInventoryValue),
      unitCost: new FormControl(materialRawValue.unitCost),
      avgDemand: new FormControl(materialRawValue.avgDemand),
      avgInventoryEffectAfterChange: new FormControl(materialRawValue.avgInventoryEffectAfterChange),
      flagMaterial: new FormControl(materialRawValue.flagMaterial),
      comment: new FormControl(materialRawValue.comment),
    });
  }

  getMaterial(form: MaterialFormGroup): IMaterial | NewMaterial {
    return form.getRawValue() as IMaterial | NewMaterial;
  }

  resetForm(form: MaterialFormGroup, material: MaterialFormGroupInput): void {
    const materialRawValue = { ...this.getFormDefaults(), ...material };
    form.reset(
      {
        ...materialRawValue,
        id: { value: materialRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): MaterialFormDefaults {
    return {
      id: null,
      flagMaterial: false,
    };
  }
}
