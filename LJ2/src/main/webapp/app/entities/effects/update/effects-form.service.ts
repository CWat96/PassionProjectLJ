import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IEffects, NewEffects } from '../effects.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IEffects for edit and NewEffectsFormGroupInput for create.
 */
type EffectsFormGroupInput = IEffects | PartialWithRequiredKeyOf<NewEffects>;

type EffectsFormDefaults = Pick<NewEffects, 'id'>;

type EffectsFormGroupContent = {
  id: FormControl<IEffects['id'] | NewEffects['id']>;
  effect: FormControl<IEffects['effect']>;
  platingMaterial: FormControl<IEffects['platingMaterial']>;
  stoneGem: FormControl<IEffects['stoneGem']>;
};

export type EffectsFormGroup = FormGroup<EffectsFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class EffectsFormService {
  createEffectsFormGroup(effects: EffectsFormGroupInput = { id: null }): EffectsFormGroup {
    const effectsRawValue = {
      ...this.getFormDefaults(),
      ...effects,
    };
    return new FormGroup<EffectsFormGroupContent>({
      id: new FormControl(
        { value: effectsRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      effect: new FormControl(effectsRawValue.effect),
      platingMaterial: new FormControl(effectsRawValue.platingMaterial),
      stoneGem: new FormControl(effectsRawValue.stoneGem),
    });
  }

  getEffects(form: EffectsFormGroup): IEffects | NewEffects {
    return form.getRawValue() as IEffects | NewEffects;
  }

  resetForm(form: EffectsFormGroup, effects: EffectsFormGroupInput): void {
    const effectsRawValue = { ...this.getFormDefaults(), ...effects };
    form.reset(
      {
        ...effectsRawValue,
        id: { value: effectsRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): EffectsFormDefaults {
    return {
      id: null,
    };
  }
}
