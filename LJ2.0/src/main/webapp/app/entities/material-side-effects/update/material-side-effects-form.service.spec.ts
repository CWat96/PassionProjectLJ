import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../material-side-effects.test-samples';

import { MaterialSideEffectsFormService } from './material-side-effects-form.service';

describe('MaterialSideEffects Form Service', () => {
  let service: MaterialSideEffectsFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MaterialSideEffectsFormService);
  });

  describe('Service methods', () => {
    describe('createMaterialSideEffectsFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createMaterialSideEffectsFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            materialSideEffectsName: expect.any(Object),
          }),
        );
      });

      it('passing IMaterialSideEffects should create a new form with FormGroup', () => {
        const formGroup = service.createMaterialSideEffectsFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            materialSideEffectsName: expect.any(Object),
          }),
        );
      });
    });

    describe('getMaterialSideEffects', () => {
      it('should return NewMaterialSideEffects for default MaterialSideEffects initial value', () => {
        const formGroup = service.createMaterialSideEffectsFormGroup(sampleWithNewData);

        const materialSideEffects = service.getMaterialSideEffects(formGroup) as any;

        expect(materialSideEffects).toMatchObject(sampleWithNewData);
      });

      it('should return NewMaterialSideEffects for empty MaterialSideEffects initial value', () => {
        const formGroup = service.createMaterialSideEffectsFormGroup();

        const materialSideEffects = service.getMaterialSideEffects(formGroup) as any;

        expect(materialSideEffects).toMatchObject({});
      });

      it('should return IMaterialSideEffects', () => {
        const formGroup = service.createMaterialSideEffectsFormGroup(sampleWithRequiredData);

        const materialSideEffects = service.getMaterialSideEffects(formGroup) as any;

        expect(materialSideEffects).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IMaterialSideEffects should not enable id FormControl', () => {
        const formGroup = service.createMaterialSideEffectsFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewMaterialSideEffects should disable id FormControl', () => {
        const formGroup = service.createMaterialSideEffectsFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
