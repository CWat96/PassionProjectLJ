import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IStoneGem } from '../stone-gem.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../stone-gem.test-samples';

import { StoneGemService } from './stone-gem.service';

const requireRestSample: IStoneGem = {
  ...sampleWithRequiredData,
};

describe('StoneGem Service', () => {
  let service: StoneGemService;
  let httpMock: HttpTestingController;
  let expectedResult: IStoneGem | IStoneGem[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(StoneGemService);
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

    it('should create a StoneGem', () => {
      const stoneGem = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(stoneGem).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a StoneGem', () => {
      const stoneGem = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(stoneGem).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a StoneGem', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of StoneGem', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a StoneGem', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addStoneGemToCollectionIfMissing', () => {
      it('should add a StoneGem to an empty array', () => {
        const stoneGem: IStoneGem = sampleWithRequiredData;
        expectedResult = service.addStoneGemToCollectionIfMissing([], stoneGem);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(stoneGem);
      });

      it('should not add a StoneGem to an array that contains it', () => {
        const stoneGem: IStoneGem = sampleWithRequiredData;
        const stoneGemCollection: IStoneGem[] = [
          {
            ...stoneGem,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addStoneGemToCollectionIfMissing(stoneGemCollection, stoneGem);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a StoneGem to an array that doesn't contain it", () => {
        const stoneGem: IStoneGem = sampleWithRequiredData;
        const stoneGemCollection: IStoneGem[] = [sampleWithPartialData];
        expectedResult = service.addStoneGemToCollectionIfMissing(stoneGemCollection, stoneGem);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(stoneGem);
      });

      it('should add only unique StoneGem to an array', () => {
        const stoneGemArray: IStoneGem[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const stoneGemCollection: IStoneGem[] = [sampleWithRequiredData];
        expectedResult = service.addStoneGemToCollectionIfMissing(stoneGemCollection, ...stoneGemArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const stoneGem: IStoneGem = sampleWithRequiredData;
        const stoneGem2: IStoneGem = sampleWithPartialData;
        expectedResult = service.addStoneGemToCollectionIfMissing([], stoneGem, stoneGem2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(stoneGem);
        expect(expectedResult).toContain(stoneGem2);
      });

      it('should accept null and undefined values', () => {
        const stoneGem: IStoneGem = sampleWithRequiredData;
        expectedResult = service.addStoneGemToCollectionIfMissing([], null, stoneGem, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(stoneGem);
      });

      it('should return initial array if no StoneGem is added', () => {
        const stoneGemCollection: IStoneGem[] = [sampleWithRequiredData];
        expectedResult = service.addStoneGemToCollectionIfMissing(stoneGemCollection, undefined, null);
        expect(expectedResult).toEqual(stoneGemCollection);
      });
    });

    describe('compareStoneGem', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareStoneGem(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareStoneGem(entity1, entity2);
        const compareResult2 = service.compareStoneGem(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareStoneGem(entity1, entity2);
        const compareResult2 = service.compareStoneGem(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareStoneGem(entity1, entity2);
        const compareResult2 = service.compareStoneGem(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
