import { IMaterialSideEffects } from 'app/entities/material-side-effects/material-side-effects.model';
import { IPlatingMaterial } from 'app/entities/plating-material/plating-material.model';
import { IStoneGem } from 'app/entities/stone-gem/stone-gem.model';

export interface IEffects {
  id: number;
  effect?: string | null;
  effects?: IMaterialSideEffects[] | null;
  platingMaterials?: IPlatingMaterial[] | null;
  stoneGems?: IStoneGem[] | null;
}

export type NewEffects = Omit<IEffects, 'id'> & { id: null };
