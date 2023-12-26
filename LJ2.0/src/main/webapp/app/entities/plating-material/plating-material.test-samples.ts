import { IPlatingMaterial, NewPlatingMaterial } from './plating-material.model';

export const sampleWithRequiredData: IPlatingMaterial = {
  id: 3577,
};

export const sampleWithPartialData: IPlatingMaterial = {
  id: 15661,
  platingName: 'pace huzzah',
};

export const sampleWithFullData: IPlatingMaterial = {
  id: 424,
  platingName: 'spokesman positively',
};

export const sampleWithNewData: NewPlatingMaterial = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
