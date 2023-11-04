import { ABCClassification } from 'app/entities/enumerations/abc-classification.model';

import { IMaterial, NewMaterial } from './material.model';

export const sampleWithRequiredData: IMaterial = {
  id: 89389,
};

export const sampleWithPartialData: IMaterial = {
  id: 26619,
  material: 22539,
  serviceLevel: 68637,
  propsedSST: 48492,
  currentSAPSafeTime: 50127,
  proposedST: 39798,
  deltaST: 36228,
  currentInventoryValue: 86237,
  unitCost: 45159,
  avgDemand: 12692,
  avgInventoryEffectAfterChange: 62819,
  comment: 'bypass',
};

export const sampleWithFullData: IMaterial = {
  id: 99662,
  material: 40812,
  description: 'human-resource Field payment',
  abcClassification: ABCClassification['C'],
  avgSupplierDelay: 75006,
  maxSupplierDelay: 70214,
  serviceLevel: 82214,
  currSAPSafetyStock: 18932,
  propsedSST: 19278,
  deltaSST: 9285,
  currentSAPSafeTime: 50460,
  proposedST: 49989,
  deltaST: 51648,
  openSAPmd04: 'intuitive Gloves',
  currentInventoryValue: 36462,
  unitCost: 43223,
  avgDemand: 59899,
  avgInventoryEffectAfterChange: 63091,
  flagMaterial: true,
  comment: 'Synchronised system payment',
};

export const sampleWithNewData: NewMaterial = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
