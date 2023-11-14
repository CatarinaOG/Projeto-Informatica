import { ABCClassification } from 'app/entities/enumerations/abc-classification.model';

import { IMaterial, NewMaterial } from './material.model';

export const sampleWithRequiredData: IMaterial = {
  id: 89389,
};

export const sampleWithPartialData: IMaterial = {
  id: 68637,
  material: 'Berkshire RSS',
  serviceLevel: 62819,
  proposedSST: 20689,
  currentSAPSafeTime: 74687,
  proposedST: 7104,
  deltaST: 54378,
  currentInventoryValue: 99662,
  unitCost: 40812,
  avgDemand: 97109,
  avgInventoryEffectAfterChange: 26213,
  newSAPSafetyTime: 48603,
};

export const sampleWithFullData: IMaterial = {
  id: 23536,
  material: 'fuchsia payment',
  description: 'Tanzanian blue Borders',
  abcClassification: ABCClassification['B'],
  avgSupplierDelay: 14209,
  maxSupplierDelay: 82111,
  serviceLevel: 18079,
  currSAPSafetyStock: 29722,
  proposedSST: 65245,
  deltaSST: 36462,
  currentSAPSafeTime: 43223,
  proposedST: 59899,
  deltaST: 63091,
  openSAPmd04: 'open-source Franc',
  currentInventoryValue: 30141,
  unitCost: 63040,
  avgDemand: 60229,
  avgInventoryEffectAfterChange: 63809,
  newSAPSafetyStock: 767,
  newSAPSafetyTime: 16878,
  flagMaterial: true,
  comment: 'Salad',
};

export const sampleWithNewData: NewMaterial = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
