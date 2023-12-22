import { IPlatingMaterial, NewPlatingMaterial } from './plating-material.model';

export const sampleWithRequiredData: IPlatingMaterial = {
  id: 10249,
};

export const sampleWithPartialData: IPlatingMaterial = {
  id: 30313,
};

export const sampleWithFullData: IPlatingMaterial = {
  id: 9290,
  platingName: 'across exploration',
};

export const sampleWithNewData: NewPlatingMaterial = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
