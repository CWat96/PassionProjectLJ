import { IPlatingMaterial } from 'app/entities/plating-material/plating-material.model';
import { IStoneGem } from 'app/entities/stone-gem/stone-gem.model';

export interface IEffects {
  id: number;
  effect?: string | null;
  platingMaterial?: IPlatingMaterial | null;
  stoneGem?: IStoneGem | null;
}

export type NewEffects = Omit<IEffects, 'id'> & { id: null };
