import { ABCClassification } from 'app/entities/enumerations/abc-classification.model';

import { IMaterial, NewMaterial } from './material.model';

export const sampleWithRequiredData: IMaterial = {
  id: 89389,
};

export const sampleWithPartialData: IMaterial = {
  id: 26619,
  material: 'Litas',
  serviceLevel: 39798,
  proposedSST: 36228,
  currentSAPSafeTime: 86237,
  proposedST: 45159,
  deltaST: 12692,
  currentInventoryValue: 62819,
  unitCost: 20689,
  avgDemand: 74687,
  avgInventoryEffectAfterChange: 7104,
  comment: 'Administrator human-resource',
};

export const sampleWithFullData: IMaterial = {
  id: 47908,
  material: 'THX',
  description: 'USB overriding Sleek',
  abcClassification: ABCClassification['B'],
  avgSupplierDelay: 64805,
  maxSupplierDelay: 36044,
  serviceLevel: 14209,
  currSAPSafetyStock: 82111,
  proposedSST: 18079,
  deltaSST: 29722,
  currentSAPSafeTime: 65245,
  proposedST: 36462,
  deltaST: 43223,
  openSAPmd04: 'payment Synchronised',
  currentInventoryValue: 30079,
  unitCost: 92917,
  avgDemand: 30141,
  avgInventoryEffectAfterChange: 63040,
  flagMaterial: true,
  comment: 'Games Small',
};

export const sampleWithNewData: NewMaterial = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
