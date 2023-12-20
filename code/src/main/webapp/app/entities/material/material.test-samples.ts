import dayjs from 'dayjs/esm';

import { ABCClassification } from 'app/entities/enumerations/abc-classification.model';
import { Coin } from 'app/entities/enumerations/coin.model';

import { IMaterial, NewMaterial } from './material.model';

export const sampleWithRequiredData: IMaterial = {
  id: 89389,
};

export const sampleWithPartialData: IMaterial = {
  id: 20689,
  material: 'Shirt human-resource Field',
  serviceLevel: 64471,
  mrpController: 'Silver Tanzanian',
  proposedSST: 19278,
  deltaSST: 9285,
  currentSAPSafeTime: 50460,
  deltaST: 49989,
  openSAPmd04: 'withdrawal hacking',
  currentInventoryValue: 65245,
  unitCost: 36462,
  avgInventoryEffectAfterChange: 43223,
  comment: 'payment Synchronised',
  newSAPSafetyTime: 30079,
  dateOfUpdatedSS: dayjs('2023-11-13'),
  currency: Coin['KRW'],
};

export const sampleWithFullData: IMaterial = {
  id: 63040,
  material: 'deposit Taka',
  description: 'Universal',
  abcClassification: ABCClassification['A'],
  avgSupplierDelay: 29936,
  maxSupplierDelay: 36553,
  serviceLevel: 31047,
  plant: 'content-based Concrete',
  mrpController: 'methodical Analyst Lats',
  currSAPSafetyStock: 99763,
  proposedSST: 26016,
  deltaSST: 43479,
  currentSAPSafeTime: 11227,
  proposedST: 46038,
  deltaST: 65089,
  openSAPmd04: 'Generic Fresh',
  currentInventoryValue: 56744,
  unitCost: 43315,
  avgDemand: 68837,
  avgInventoryEffectAfterChange: 94596,
  flagMaterial: false,
  flagExpirationDate: dayjs('2023-11-14'),
  comment: 'Indonesia',
  newSAPSafetyStock: 85934,
  newSAPSafetyTime: 76196,
  valueOfUpdatedSS: 58299,
  valueOfUpdatedST: 60764,
  dateOfUpdatedSS: dayjs('2023-11-14'),
  dateOfUpdatedST: dayjs('2023-11-14'),
  toSaveUpdates: false,
  currency: Coin['TWD'],
};

export const sampleWithNewData: NewMaterial = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
