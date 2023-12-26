import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IMaterialSideEffects } from '../material-side-effects.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../material-side-effects.test-samples';

import { MaterialSideEffectsService } from './material-side-effects.service';

const requireRestSample: IMaterialSideEffects = {
  ...sampleWithRequiredData,
};

describe('MaterialSideEffects Service', () => {
  let service: MaterialSideEffectsService;
  let httpMock: HttpTestingController;
  let expectedResult: IMaterialSideEffects | IMaterialSideEffects[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(MaterialSideEffectsService);
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

    it('should create a MaterialSideEffects', () => {
      const materialSideEffects = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(materialSideEffects).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a MaterialSideEffects', () => {
      const materialSideEffects = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(materialSideEffects).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a MaterialSideEffects', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of MaterialSideEffects', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a MaterialSideEffects', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addMaterialSideEffectsToCollectionIfMissing', () => {
      it('should add a MaterialSideEffects to an empty array', () => {
        const materialSideEffects: IMaterialSideEffects = sampleWithRequiredData;
        expectedResult = service.addMaterialSideEffectsToCollectionIfMissing([], materialSideEffects);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(materialSideEffects);
      });

      it('should not add a MaterialSideEffects to an array that contains it', () => {
        const materialSideEffects: IMaterialSideEffects = sampleWithRequiredData;
        const materialSideEffectsCollection: IMaterialSideEffects[] = [
          {
            ...materialSideEffects,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addMaterialSideEffectsToCollectionIfMissing(materialSideEffectsCollection, materialSideEffects);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a MaterialSideEffects to an array that doesn't contain it", () => {
        const materialSideEffects: IMaterialSideEffects = sampleWithRequiredData;
        const materialSideEffectsCollection: IMaterialSideEffects[] = [sampleWithPartialData];
        expectedResult = service.addMaterialSideEffectsToCollectionIfMissing(materialSideEffectsCollection, materialSideEffects);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(materialSideEffects);
      });

      it('should add only unique MaterialSideEffects to an array', () => {
        const materialSideEffectsArray: IMaterialSideEffects[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const materialSideEffectsCollection: IMaterialSideEffects[] = [sampleWithRequiredData];
        expectedResult = service.addMaterialSideEffectsToCollectionIfMissing(materialSideEffectsCollection, ...materialSideEffectsArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const materialSideEffects: IMaterialSideEffects = sampleWithRequiredData;
        const materialSideEffects2: IMaterialSideEffects = sampleWithPartialData;
        expectedResult = service.addMaterialSideEffectsToCollectionIfMissing([], materialSideEffects, materialSideEffects2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(materialSideEffects);
        expect(expectedResult).toContain(materialSideEffects2);
      });

      it('should accept null and undefined values', () => {
        const materialSideEffects: IMaterialSideEffects = sampleWithRequiredData;
        expectedResult = service.addMaterialSideEffectsToCollectionIfMissing([], null, materialSideEffects, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(materialSideEffects);
      });

      it('should return initial array if no MaterialSideEffects is added', () => {
        const materialSideEffectsCollection: IMaterialSideEffects[] = [sampleWithRequiredData];
        expectedResult = service.addMaterialSideEffectsToCollectionIfMissing(materialSideEffectsCollection, undefined, null);
        expect(expectedResult).toEqual(materialSideEffectsCollection);
      });
    });

    describe('compareMaterialSideEffects', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareMaterialSideEffects(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareMaterialSideEffects(entity1, entity2);
        const compareResult2 = service.compareMaterialSideEffects(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareMaterialSideEffects(entity1, entity2);
        const compareResult2 = service.compareMaterialSideEffects(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareMaterialSideEffects(entity1, entity2);
        const compareResult2 = service.compareMaterialSideEffects(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
