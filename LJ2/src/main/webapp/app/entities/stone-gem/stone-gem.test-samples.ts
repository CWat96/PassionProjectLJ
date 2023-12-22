import { IStoneGem, NewStoneGem } from './stone-gem.model';

export const sampleWithRequiredData: IStoneGem = {
  id: 23060,
};

export const sampleWithPartialData: IStoneGem = {
  id: 5002,
};

export const sampleWithFullData: IStoneGem = {
  id: 7496,
  stoneGemName: 'overfly',
};

export const sampleWithNewData: NewStoneGem = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
