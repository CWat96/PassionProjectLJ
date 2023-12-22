import { IEffects } from 'app/entities/effects/effects.model';

export interface IPlatingMaterial {
  id: number;
  platingName?: string | null;
  effects?: IEffects[] | null;
}

export type NewPlatingMaterial = Omit<IPlatingMaterial, 'id'> & { id: null };
