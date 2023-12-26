import { IEffects } from 'app/entities/effects/effects.model';
import { IMaterialSideEffects } from 'app/entities/material-side-effects/material-side-effects.model';

export interface IPlatingMaterial {
  id: number;
  platingName?: string | null;
  effects?: IEffects[] | null;
  materialsideeffects?: IMaterialSideEffects[] | null;
}

export type NewPlatingMaterial = Omit<IPlatingMaterial, 'id'> & { id: null };
