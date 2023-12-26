import { IMaterialSideEffects, NewMaterialSideEffects } from './material-side-effects.model';

export const sampleWithRequiredData: IMaterialSideEffects = {
  id: 31649,
};

export const sampleWithPartialData: IMaterialSideEffects = {
  id: 19814,
  materialSideEffectsName: 'failing rehearse jaded',
};

export const sampleWithFullData: IMaterialSideEffects = {
  id: 18823,
  materialSideEffectsName: 'phew hm when',
};

export const sampleWithNewData: NewMaterialSideEffects = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
