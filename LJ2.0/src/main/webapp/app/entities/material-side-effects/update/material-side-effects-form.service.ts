import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IMaterialSideEffects, NewMaterialSideEffects } from '../material-side-effects.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IMaterialSideEffects for edit and NewMaterialSideEffectsFormGroupInput for create.
 */
type MaterialSideEffectsFormGroupInput = IMaterialSideEffects | PartialWithRequiredKeyOf<NewMaterialSideEffects>;

type MaterialSideEffectsFormDefaults = Pick<NewMaterialSideEffects, 'id'>;

type MaterialSideEffectsFormGroupContent = {
  id: FormControl<IMaterialSideEffects['id'] | NewMaterialSideEffects['id']>;
  materialSideEffectsName: FormControl<IMaterialSideEffects['materialSideEffectsName']>;
};

export type MaterialSideEffectsFormGroup = FormGroup<MaterialSideEffectsFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class MaterialSideEffectsFormService {
  createMaterialSideEffectsFormGroup(materialSideEffects: MaterialSideEffectsFormGroupInput = { id: null }): MaterialSideEffectsFormGroup {
    const materialSideEffectsRawValue = {
      ...this.getFormDefaults(),
      ...materialSideEffects,
    };
    return new FormGroup<MaterialSideEffectsFormGroupContent>({
      id: new FormControl(
        { value: materialSideEffectsRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      materialSideEffectsName: new FormControl(materialSideEffectsRawValue.materialSideEffectsName),
    });
  }

  getMaterialSideEffects(form: MaterialSideEffectsFormGroup): IMaterialSideEffects | NewMaterialSideEffects {
    return form.getRawValue() as IMaterialSideEffects | NewMaterialSideEffects;
  }

  resetForm(form: MaterialSideEffectsFormGroup, materialSideEffects: MaterialSideEffectsFormGroupInput): void {
    const materialSideEffectsRawValue = { ...this.getFormDefaults(), ...materialSideEffects };
    form.reset(
      {
        ...materialSideEffectsRawValue,
        id: { value: materialSideEffectsRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): MaterialSideEffectsFormDefaults {
    return {
      id: null,
    };
  }
}
