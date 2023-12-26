import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IPlatingMaterial } from '../plating-material.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../plating-material.test-samples';

import { PlatingMaterialService } from './plating-material.service';

const requireRestSample: IPlatingMaterial = {
  ...sampleWithRequiredData,
};

describe('PlatingMaterial Service', () => {
  let service: PlatingMaterialService;
  let httpMock: HttpTestingController;
  let expectedResult: IPlatingMaterial | IPlatingMaterial[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(PlatingMaterialService);
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

    it('should create a PlatingMaterial', () => {
      const platingMaterial = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(platingMaterial).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a PlatingMaterial', () => {
      const platingMaterial = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(platingMaterial).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a PlatingMaterial', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of PlatingMaterial', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a PlatingMaterial', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addPlatingMaterialToCollectionIfMissing', () => {
      it('should add a PlatingMaterial to an empty array', () => {
        const platingMaterial: IPlatingMaterial = sampleWithRequiredData;
        expectedResult = service.addPlatingMaterialToCollectionIfMissing([], platingMaterial);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(platingMaterial);
      });

      it('should not add a PlatingMaterial to an array that contains it', () => {
        const platingMaterial: IPlatingMaterial = sampleWithRequiredData;
        const platingMaterialCollection: IPlatingMaterial[] = [
          {
            ...platingMaterial,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addPlatingMaterialToCollectionIfMissing(platingMaterialCollection, platingMaterial);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a PlatingMaterial to an array that doesn't contain it", () => {
        const platingMaterial: IPlatingMaterial = sampleWithRequiredData;
        const platingMaterialCollection: IPlatingMaterial[] = [sampleWithPartialData];
        expectedResult = service.addPlatingMaterialToCollectionIfMissing(platingMaterialCollection, platingMaterial);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(platingMaterial);
      });

      it('should add only unique PlatingMaterial to an array', () => {
        const platingMaterialArray: IPlatingMaterial[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const platingMaterialCollection: IPlatingMaterial[] = [sampleWithRequiredData];
        expectedResult = service.addPlatingMaterialToCollectionIfMissing(platingMaterialCollection, ...platingMaterialArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const platingMaterial: IPlatingMaterial = sampleWithRequiredData;
        const platingMaterial2: IPlatingMaterial = sampleWithPartialData;
        expectedResult = service.addPlatingMaterialToCollectionIfMissing([], platingMaterial, platingMaterial2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(platingMaterial);
        expect(expectedResult).toContain(platingMaterial2);
      });

      it('should accept null and undefined values', () => {
        const platingMaterial: IPlatingMaterial = sampleWithRequiredData;
        expectedResult = service.addPlatingMaterialToCollectionIfMissing([], null, platingMaterial, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(platingMaterial);
      });

      it('should return initial array if no PlatingMaterial is added', () => {
        const platingMaterialCollection: IPlatingMaterial[] = [sampleWithRequiredData];
        expectedResult = service.addPlatingMaterialToCollectionIfMissing(platingMaterialCollection, undefined, null);
        expect(expectedResult).toEqual(platingMaterialCollection);
      });
    });

    describe('comparePlatingMaterial', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.comparePlatingMaterial(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.comparePlatingMaterial(entity1, entity2);
        const compareResult2 = service.comparePlatingMaterial(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.comparePlatingMaterial(entity1, entity2);
        const compareResult2 = service.comparePlatingMaterial(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.comparePlatingMaterial(entity1, entity2);
        const compareResult2 = service.comparePlatingMaterial(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
