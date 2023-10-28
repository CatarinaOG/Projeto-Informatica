import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../diabo.test-samples';

import { DiaboFormService } from './diabo-form.service';

describe('Diabo Form Service', () => {
  let service: DiaboFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DiaboFormService);
  });

  describe('Service methods', () => {
    describe('createDiaboFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createDiaboFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            material: expect.any(Object),
            materialDescription: expect.any(Object),
            abcClassification: expect.any(Object),
            avgSupplierDelayLast90Days: expect.any(Object),
            maxSupplierDelayLast90Days: expect.any(Object),
            serviceLevel: expect.any(Object),
            currentSapSafetyStock: expect.any(Object),
            proposedSafetyStock: expect.any(Object),
            deltaSafetyStock: expect.any(Object),
            currentSapSafetyTime: expect.any(Object),
            proposedSafetyTime: expect.any(Object),
            deltaSafetyTime: expect.any(Object),
            openSapMd04: expect.any(Object),
            currentInventoryValue: expect.any(Object),
            averageInventoryEffectAfterChange: expect.any(Object),
            newSapSafetyStock: expect.any(Object),
            newSapSafetyTime: expect.any(Object),
            selectEntriesForChangeInSap: expect.any(Object),
            flagMaterialAsSpecialCase: expect.any(Object),
            commentary: expect.any(Object),
          })
        );
      });

      it('passing IDiabo should create a new form with FormGroup', () => {
        const formGroup = service.createDiaboFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            material: expect.any(Object),
            materialDescription: expect.any(Object),
            abcClassification: expect.any(Object),
            avgSupplierDelayLast90Days: expect.any(Object),
            maxSupplierDelayLast90Days: expect.any(Object),
            serviceLevel: expect.any(Object),
            currentSapSafetyStock: expect.any(Object),
            proposedSafetyStock: expect.any(Object),
            deltaSafetyStock: expect.any(Object),
            currentSapSafetyTime: expect.any(Object),
            proposedSafetyTime: expect.any(Object),
            deltaSafetyTime: expect.any(Object),
            openSapMd04: expect.any(Object),
            currentInventoryValue: expect.any(Object),
            averageInventoryEffectAfterChange: expect.any(Object),
            newSapSafetyStock: expect.any(Object),
            newSapSafetyTime: expect.any(Object),
            selectEntriesForChangeInSap: expect.any(Object),
            flagMaterialAsSpecialCase: expect.any(Object),
            commentary: expect.any(Object),
          })
        );
      });
    });

    describe('getDiabo', () => {
      it('should return NewDiabo for default Diabo initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createDiaboFormGroup(sampleWithNewData);

        const diabo = service.getDiabo(formGroup) as any;

        expect(diabo).toMatchObject(sampleWithNewData);
      });

      it('should return NewDiabo for empty Diabo initial value', () => {
        const formGroup = service.createDiaboFormGroup();

        const diabo = service.getDiabo(formGroup) as any;

        expect(diabo).toMatchObject({});
      });

      it('should return IDiabo', () => {
        const formGroup = service.createDiaboFormGroup(sampleWithRequiredData);

        const diabo = service.getDiabo(formGroup) as any;

        expect(diabo).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IDiabo should not enable id FormControl', () => {
        const formGroup = service.createDiaboFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewDiabo should disable id FormControl', () => {
        const formGroup = service.createDiaboFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
