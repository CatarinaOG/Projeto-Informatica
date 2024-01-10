import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../flagged-material.test-samples';

import { FlaggedMaterialFormService } from './flagged-material-form.service';

describe('FlaggedMaterial Form Service', () => {
  let service: FlaggedMaterialFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FlaggedMaterialFormService);
  });

  describe('Service methods', () => {
    describe('createFlaggedMaterialFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createFlaggedMaterialFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            material: expect.any(Object),
            description: expect.any(Object),
            abcClassification: expect.any(Object),
            plant: expect.any(Object),
            mrpController: expect.any(Object),
            flagMaterial: expect.any(Object),
            flagExpirationDate: expect.any(Object),
          })
        );
      });

      it('passing IFlaggedMaterial should create a new form with FormGroup', () => {
        const formGroup = service.createFlaggedMaterialFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            material: expect.any(Object),
            description: expect.any(Object),
            abcClassification: expect.any(Object),
            plant: expect.any(Object),
            mrpController: expect.any(Object),
            flagMaterial: expect.any(Object),
            flagExpirationDate: expect.any(Object),
          })
        );
      });
    });

    describe('getFlaggedMaterial', () => {
      it('should return NewFlaggedMaterial for default FlaggedMaterial initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createFlaggedMaterialFormGroup(sampleWithNewData);

        const flaggedMaterial = service.getFlaggedMaterial(formGroup) as any;

        expect(flaggedMaterial).toMatchObject(sampleWithNewData);
      });

      it('should return NewFlaggedMaterial for empty FlaggedMaterial initial value', () => {
        const formGroup = service.createFlaggedMaterialFormGroup();

        const flaggedMaterial = service.getFlaggedMaterial(formGroup) as any;

        expect(flaggedMaterial).toMatchObject({});
      });

      it('should return IFlaggedMaterial', () => {
        const formGroup = service.createFlaggedMaterialFormGroup(sampleWithRequiredData);

        const flaggedMaterial = service.getFlaggedMaterial(formGroup) as any;

        expect(flaggedMaterial).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IFlaggedMaterial should not enable id FormControl', () => {
        const formGroup = service.createFlaggedMaterialFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewFlaggedMaterial should disable id FormControl', () => {
        const formGroup = service.createFlaggedMaterialFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
