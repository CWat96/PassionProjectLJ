import { IEffects } from 'app/entities/effects/effects.model';

export interface IStoneGem {
  id: number;
  stoneGemName?: string | null;
  effects?: IEffects[] | null;
}

export type NewStoneGem = Omit<IStoneGem, 'id'> & { id: null };
