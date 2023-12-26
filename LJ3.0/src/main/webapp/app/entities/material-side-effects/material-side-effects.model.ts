import { IPlatingMaterial } from 'app/entities/plating-material/plating-material.model';
import { IEffects } from 'app/entities/effects/effects.model';

export interface IMaterialSideEffects {
  id: number;
  materialSideEffectsName?: string | null;
  platingMaterials?: IPlatingMaterial[] | null;
  effects?: IEffects[] | null;
}

export type NewMaterialSideEffects = Omit<IMaterialSideEffects, 'id'> & { id: null };
