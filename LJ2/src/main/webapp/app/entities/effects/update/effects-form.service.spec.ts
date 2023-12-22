import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../effects.test-samples';

import { EffectsFormService } from './effects-form.service';

describe('Effects Form Service', () => {
  let service: EffectsFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EffectsFormService);
  });

  describe('Service methods', () => {
    describe('createEffectsFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createEffectsFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            effect: expect.any(Object),
            platingMaterial: expect.any(Object),
            stoneGem: expect.any(Object),
          }),
        );
      });

      it('passing IEffects should create a new form with FormGroup', () => {
        const formGroup = service.createEffectsFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            effect: expect.any(Object),
            platingMaterial: expect.any(Object),
            stoneGem: expect.any(Object),
          }),
        );
      });
    });

    describe('getEffects', () => {
      it('should return NewEffects for default Effects initial value', () => {
        const formGroup = service.createEffectsFormGroup(sampleWithNewData);

        const effects = service.getEffects(formGroup) as any;

        expect(effects).toMatchObject(sampleWithNewData);
      });

      it('should return NewEffects for empty Effects initial value', () => {
        const formGroup = service.createEffectsFormGroup();

        const effects = service.getEffects(formGroup) as any;

        expect(effects).toMatchObject({});
      });

      it('should return IEffects', () => {
        const formGroup = service.createEffectsFormGroup(sampleWithRequiredData);

        const effects = service.getEffects(formGroup) as any;

        expect(effects).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IEffects should not enable id FormControl', () => {
        const formGroup = service.createEffectsFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewEffects should disable id FormControl', () => {
        const formGroup = service.createEffectsFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
