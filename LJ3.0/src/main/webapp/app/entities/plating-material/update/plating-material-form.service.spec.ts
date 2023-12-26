import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../plating-material.test-samples';

import { PlatingMaterialFormService } from './plating-material-form.service';

describe('PlatingMaterial Form Service', () => {
  let service: PlatingMaterialFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PlatingMaterialFormService);
  });

  describe('Service methods', () => {
    describe('createPlatingMaterialFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createPlatingMaterialFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            platingName: expect.any(Object),
            effects: expect.any(Object),
            materialsideeffects: expect.any(Object),
          }),
        );
      });

      it('passing IPlatingMaterial should create a new form with FormGroup', () => {
        const formGroup = service.createPlatingMaterialFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            platingName: expect.any(Object),
            effects: expect.any(Object),
            materialsideeffects: expect.any(Object),
          }),
        );
      });
    });

    describe('getPlatingMaterial', () => {
      it('should return NewPlatingMaterial for default PlatingMaterial initial value', () => {
        const formGroup = service.createPlatingMaterialFormGroup(sampleWithNewData);

        const platingMaterial = service.getPlatingMaterial(formGroup) as any;

        expect(platingMaterial).toMatchObject(sampleWithNewData);
      });

      it('should return NewPlatingMaterial for empty PlatingMaterial initial value', () => {
        const formGroup = service.createPlatingMaterialFormGroup();

        const platingMaterial = service.getPlatingMaterial(formGroup) as any;

        expect(platingMaterial).toMatchObject({});
      });

      it('should return IPlatingMaterial', () => {
        const formGroup = service.createPlatingMaterialFormGroup(sampleWithRequiredData);

        const platingMaterial = service.getPlatingMaterial(formGroup) as any;

        expect(platingMaterial).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IPlatingMaterial should not enable id FormControl', () => {
        const formGroup = service.createPlatingMaterialFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewPlatingMaterial should disable id FormControl', () => {
        const formGroup = service.createPlatingMaterialFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
