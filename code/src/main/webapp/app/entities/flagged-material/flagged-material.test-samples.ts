import dayjs from 'dayjs/esm';

import { ABCClassification } from 'app/entities/enumerations/abc-classification.model';
import { Coin } from 'app/entities/enumerations/coin.model';

import { IFlaggedMaterial, NewFlaggedMaterial } from './flagged-material.model';

export const sampleWithRequiredData: IFlaggedMaterial = {
  id: 71808,
};

export const sampleWithPartialData: IFlaggedMaterial = {
  id: 94112,
  material: 'open-source digital',
  description: 'unleash Fresh navigating',
  abcClassification: ABCClassification['C'],
  avgSupplierDelay: 56702,
  serviceLevel: 42920,
  currSAPSafetyStock: 22849,
  proposedSST: 84434,
  deltaSST: 5260,
  currentSAPSafeTime: 46266,
  deltaST: 46583,
  unitCost: 2580,
  avgDemand: 64250,
  avgInventoryEffectAfterChange: 25284,
  flagMaterial: false,
  dateOfUpdatedST: dayjs('2023-12-20'),
  toSaveUpdates: true,
};

export const sampleWithFullData: IFlaggedMaterial = {
  id: 2792,
  material: 'Security architect',
  description: 'neural violet',
  abcClassification: ABCClassification['A'],
  avgSupplierDelay: 61683,
  maxSupplierDelay: 54704,
  serviceLevel: 2081,
  plant: 'Liaison',
  mrpController: 'mobile networks calculating',
  currSAPSafetyStock: 7354,
  proposedSST: 32275,
  deltaSST: 30311,
  currentSAPSafeTime: 93298,
  proposedST: 29619,
  deltaST: 93355,
  openSAPmd04: 'District upward-trending impactful',
  currentInventoryValue: 94348,
  unitCost: 58717,
  avgDemand: 85417,
  avgInventoryEffectAfterChange: 28847,
  flagMaterial: true,
  flagExpirationDate: dayjs('2023-12-20'),
  valueOfUpdatedSS: 26468,
  valueOfUpdatedST: 87333,
  dateOfUpdatedSS: dayjs('2023-12-20'),
  dateOfUpdatedST: dayjs('2023-12-20'),
  toSaveUpdates: true,
  currency: Coin['BHD'],
};

export const sampleWithNewData: NewFlaggedMaterial = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
