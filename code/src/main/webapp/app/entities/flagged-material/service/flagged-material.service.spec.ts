import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IFlaggedMaterial } from '../flagged-material.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../flagged-material.test-samples';

import { FlaggedMaterialService, RestFlaggedMaterial } from './flagged-material.service';

const requireRestSample: RestFlaggedMaterial = {
  ...sampleWithRequiredData,
  flagExpirationDate: sampleWithRequiredData.flagExpirationDate?.format(DATE_FORMAT),
};

describe('FlaggedMaterial Service', () => {
  let service: FlaggedMaterialService;
  let httpMock: HttpTestingController;
  let expectedResult: IFlaggedMaterial | IFlaggedMaterial[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(FlaggedMaterialService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a FlaggedMaterial', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const flaggedMaterial = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(flaggedMaterial).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a FlaggedMaterial', () => {
      const flaggedMaterial = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(flaggedMaterial).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a FlaggedMaterial', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of FlaggedMaterial', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a FlaggedMaterial', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addFlaggedMaterialToCollectionIfMissing', () => {
      it('should add a FlaggedMaterial to an empty array', () => {
        const flaggedMaterial: IFlaggedMaterial = sampleWithRequiredData;
        expectedResult = service.addFlaggedMaterialToCollectionIfMissing([], flaggedMaterial);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(flaggedMaterial);
      });

      it('should not add a FlaggedMaterial to an array that contains it', () => {
        const flaggedMaterial: IFlaggedMaterial = sampleWithRequiredData;
        const flaggedMaterialCollection: IFlaggedMaterial[] = [
          {
            ...flaggedMaterial,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addFlaggedMaterialToCollectionIfMissing(flaggedMaterialCollection, flaggedMaterial);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a FlaggedMaterial to an array that doesn't contain it", () => {
        const flaggedMaterial: IFlaggedMaterial = sampleWithRequiredData;
        const flaggedMaterialCollection: IFlaggedMaterial[] = [sampleWithPartialData];
        expectedResult = service.addFlaggedMaterialToCollectionIfMissing(flaggedMaterialCollection, flaggedMaterial);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(flaggedMaterial);
      });

      it('should add only unique FlaggedMaterial to an array', () => {
        const flaggedMaterialArray: IFlaggedMaterial[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const flaggedMaterialCollection: IFlaggedMaterial[] = [sampleWithRequiredData];
        expectedResult = service.addFlaggedMaterialToCollectionIfMissing(flaggedMaterialCollection, ...flaggedMaterialArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const flaggedMaterial: IFlaggedMaterial = sampleWithRequiredData;
        const flaggedMaterial2: IFlaggedMaterial = sampleWithPartialData;
        expectedResult = service.addFlaggedMaterialToCollectionIfMissing([], flaggedMaterial, flaggedMaterial2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(flaggedMaterial);
        expect(expectedResult).toContain(flaggedMaterial2);
      });

      it('should accept null and undefined values', () => {
        const flaggedMaterial: IFlaggedMaterial = sampleWithRequiredData;
        expectedResult = service.addFlaggedMaterialToCollectionIfMissing([], null, flaggedMaterial, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(flaggedMaterial);
      });

      it('should return initial array if no FlaggedMaterial is added', () => {
        const flaggedMaterialCollection: IFlaggedMaterial[] = [sampleWithRequiredData];
        expectedResult = service.addFlaggedMaterialToCollectionIfMissing(flaggedMaterialCollection, undefined, null);
        expect(expectedResult).toEqual(flaggedMaterialCollection);
      });
    });

    describe('compareFlaggedMaterial', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareFlaggedMaterial(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareFlaggedMaterial(entity1, entity2);
        const compareResult2 = service.compareFlaggedMaterial(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareFlaggedMaterial(entity1, entity2);
        const compareResult2 = service.compareFlaggedMaterial(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareFlaggedMaterial(entity1, entity2);
        const compareResult2 = service.compareFlaggedMaterial(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
