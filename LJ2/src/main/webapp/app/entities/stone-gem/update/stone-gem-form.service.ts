import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IStoneGem, NewStoneGem } from '../stone-gem.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IStoneGem for edit and NewStoneGemFormGroupInput for create.
 */
type StoneGemFormGroupInput = IStoneGem | PartialWithRequiredKeyOf<NewStoneGem>;

type StoneGemFormDefaults = Pick<NewStoneGem, 'id'>;

type StoneGemFormGroupContent = {
  id: FormControl<IStoneGem['id'] | NewStoneGem['id']>;
  stoneGemName: FormControl<IStoneGem['stoneGemName']>;
};

export type StoneGemFormGroup = FormGroup<StoneGemFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class StoneGemFormService {
  createStoneGemFormGroup(stoneGem: StoneGemFormGroupInput = { id: null }): StoneGemFormGroup {
    const stoneGemRawValue = {
      ...this.getFormDefaults(),
      ...stoneGem,
    };
    return new FormGroup<StoneGemFormGroupContent>({
      id: new FormControl(
        { value: stoneGemRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      stoneGemName: new FormControl(stoneGemRawValue.stoneGemName),
    });
  }

  getStoneGem(form: StoneGemFormGroup): IStoneGem | NewStoneGem {
    return form.getRawValue() as IStoneGem | NewStoneGem;
  }

  resetForm(form: StoneGemFormGroup, stoneGem: StoneGemFormGroupInput): void {
    const stoneGemRawValue = { ...this.getFormDefaults(), ...stoneGem };
    form.reset(
      {
        ...stoneGemRawValue,
        id: { value: stoneGemRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): StoneGemFormDefaults {
    return {
      id: null,
    };
  }
}
