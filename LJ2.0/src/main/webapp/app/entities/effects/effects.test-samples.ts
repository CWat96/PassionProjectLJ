import { IEffects, NewEffects } from './effects.model';

export const sampleWithRequiredData: IEffects = {
  id: 17970,
};

export const sampleWithPartialData: IEffects = {
  id: 30141,
  effect: 'vice why',
};

export const sampleWithFullData: IEffects = {
  id: 11075,
  effect: 'logic triumph',
};

export const sampleWithNewData: NewEffects = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
