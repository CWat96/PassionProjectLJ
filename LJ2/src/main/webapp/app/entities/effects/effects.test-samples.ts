import { IEffects, NewEffects } from './effects.model';

export const sampleWithRequiredData: IEffects = {
  id: 2359,
};

export const sampleWithPartialData: IEffects = {
  id: 3479,
};

export const sampleWithFullData: IEffects = {
  id: 1965,
  effect: 'ideal',
};

export const sampleWithNewData: NewEffects = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
