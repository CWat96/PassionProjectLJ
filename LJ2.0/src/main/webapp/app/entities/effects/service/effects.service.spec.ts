import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IEffects } from '../effects.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../effects.test-samples';

import { EffectsService } from './effects.service';

const requireRestSample: IEffects = {
  ...sampleWithRequiredData,
};

describe('Effects Service', () => {
  let service: EffectsService;
  let httpMock: HttpTestingController;
  let expectedResult: IEffects | IEffects[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(EffectsService);
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

    it('should create a Effects', () => {
      const effects = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(effects).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Effects', () => {
      const effects = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(effects).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Effects', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Effects', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Effects', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addEffectsToCollectionIfMissing', () => {
      it('should add a Effects to an empty array', () => {
        const effects: IEffects = sampleWithRequiredData;
        expectedResult = service.addEffectsToCollectionIfMissing([], effects);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(effects);
      });

      it('should not add a Effects to an array that contains it', () => {
        const effects: IEffects = sampleWithRequiredData;
        const effectsCollection: IEffects[] = [
          {
            ...effects,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addEffectsToCollectionIfMissing(effectsCollection, effects);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Effects to an array that doesn't contain it", () => {
        const effects: IEffects = sampleWithRequiredData;
        const effectsCollection: IEffects[] = [sampleWithPartialData];
        expectedResult = service.addEffectsToCollectionIfMissing(effectsCollection, effects);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(effects);
      });

      it('should add only unique Effects to an array', () => {
        const effectsArray: IEffects[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const effectsCollection: IEffects[] = [sampleWithRequiredData];
        expectedResult = service.addEffectsToCollectionIfMissing(effectsCollection, ...effectsArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const effects: IEffects = sampleWithRequiredData;
        const effects2: IEffects = sampleWithPartialData;
        expectedResult = service.addEffectsToCollectionIfMissing([], effects, effects2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(effects);
        expect(expectedResult).toContain(effects2);
      });

      it('should accept null and undefined values', () => {
        const effects: IEffects = sampleWithRequiredData;
        expectedResult = service.addEffectsToCollectionIfMissing([], null, effects, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(effects);
      });

      it('should return initial array if no Effects is added', () => {
        const effectsCollection: IEffects[] = [sampleWithRequiredData];
        expectedResult = service.addEffectsToCollectionIfMissing(effectsCollection, undefined, null);
        expect(expectedResult).toEqual(effectsCollection);
      });
    });

    describe('compareEffects', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareEffects(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareEffects(entity1, entity2);
        const compareResult2 = service.compareEffects(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareEffects(entity1, entity2);
        const compareResult2 = service.compareEffects(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareEffects(entity1, entity2);
        const compareResult2 = service.compareEffects(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
