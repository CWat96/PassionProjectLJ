import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../stone-gem.test-samples';

import { StoneGemFormService } from './stone-gem-form.service';

describe('StoneGem Form Service', () => {
  let service: StoneGemFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(StoneGemFormService);
  });

  describe('Service methods', () => {
    describe('createStoneGemFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createStoneGemFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            stoneGemName: expect.any(Object),
            effects: expect.any(Object),
          }),
        );
      });

      it('passing IStoneGem should create a new form with FormGroup', () => {
        const formGroup = service.createStoneGemFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            stoneGemName: expect.any(Object),
            effects: expect.any(Object),
          }),
        );
      });
    });

    describe('getStoneGem', () => {
      it('should return NewStoneGem for default StoneGem initial value', () => {
        const formGroup = service.createStoneGemFormGroup(sampleWithNewData);

        const stoneGem = service.getStoneGem(formGroup) as any;

        expect(stoneGem).toMatchObject(sampleWithNewData);
      });

      it('should return NewStoneGem for empty StoneGem initial value', () => {
        const formGroup = service.createStoneGemFormGroup();

        const stoneGem = service.getStoneGem(formGroup) as any;

        expect(stoneGem).toMatchObject({});
      });

      it('should return IStoneGem', () => {
        const formGroup = service.createStoneGemFormGroup(sampleWithRequiredData);

        const stoneGem = service.getStoneGem(formGroup) as any;

        expect(stoneGem).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IStoneGem should not enable id FormControl', () => {
        const formGroup = service.createStoneGemFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewStoneGem should disable id FormControl', () => {
        const formGroup = service.createStoneGemFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
