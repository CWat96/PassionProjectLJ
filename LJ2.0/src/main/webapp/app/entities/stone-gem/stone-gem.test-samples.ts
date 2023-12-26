import { IStoneGem, NewStoneGem } from './stone-gem.model';

export const sampleWithRequiredData: IStoneGem = {
  id: 25824,
};

export const sampleWithPartialData: IStoneGem = {
  id: 8226,
  stoneGemName: 'aw',
};

export const sampleWithFullData: IStoneGem = {
  id: 6658,
  stoneGemName: 'fast boohoo worriedly',
};

export const sampleWithNewData: NewStoneGem = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
