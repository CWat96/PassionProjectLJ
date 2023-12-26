import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IPlatingMaterial, NewPlatingMaterial } from '../plating-material.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IPlatingMaterial for edit and NewPlatingMaterialFormGroupInput for create.
 */
type PlatingMaterialFormGroupInput = IPlatingMaterial | PartialWithRequiredKeyOf<NewPlatingMaterial>;

type PlatingMaterialFormDefaults = Pick<NewPlatingMaterial, 'id' | 'effects' | 'materialsideeffects'>;

type PlatingMaterialFormGroupContent = {
  id: FormControl<IPlatingMaterial['id'] | NewPlatingMaterial['id']>;
  platingName: FormControl<IPlatingMaterial['platingName']>;
  effects: FormControl<IPlatingMaterial['effects']>;
  materialsideeffects: FormControl<IPlatingMaterial['materialsideeffects']>;
};

export type PlatingMaterialFormGroup = FormGroup<PlatingMaterialFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class PlatingMaterialFormService {
  createPlatingMaterialFormGroup(platingMaterial: PlatingMaterialFormGroupInput = { id: null }): PlatingMaterialFormGroup {
    const platingMaterialRawValue = {
      ...this.getFormDefaults(),
      ...platingMaterial,
    };
    return new FormGroup<PlatingMaterialFormGroupContent>({
      id: new FormControl(
        { value: platingMaterialRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      platingName: new FormControl(platingMaterialRawValue.platingName),
      effects: new FormControl(platingMaterialRawValue.effects ?? []),
      materialsideeffects: new FormControl(platingMaterialRawValue.materialsideeffects ?? []),
    });
  }

  getPlatingMaterial(form: PlatingMaterialFormGroup): IPlatingMaterial | NewPlatingMaterial {
    return form.getRawValue() as IPlatingMaterial | NewPlatingMaterial;
  }

  resetForm(form: PlatingMaterialFormGroup, platingMaterial: PlatingMaterialFormGroupInput): void {
    const platingMaterialRawValue = { ...this.getFormDefaults(), ...platingMaterial };
    form.reset(
      {
        ...platingMaterialRawValue,
        id: { value: platingMaterialRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): PlatingMaterialFormDefaults {
    return {
      id: null,
      effects: [],
      materialsideeffects: [],
    };
  }
}
