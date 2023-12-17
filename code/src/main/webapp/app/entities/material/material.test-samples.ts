import dayjs from 'dayjs/esm';

import { ABCClassification } from 'app/entities/enumerations/abc-classification.model';
import { Coin } from 'app/entities/enumerations/coin.model';

import { IMaterial, NewMaterial } from './material.model';

export const sampleWithRequiredData: IMaterial = {
  id: 89389,
};

export const sampleWithPartialData: IMaterial = {
  id: 12692,
  material: 'ivory Zimbabwe',
  serviceLevel: 97109,
  mrpController: 'Bedfordshire',
  proposedSST: 21460,
  deltaSST: 86597,
  currentSAPSafeTime: 64471,
  deltaST: 62991,
  openSAPmd04: 'USB overriding Sleek',
  currentInventoryValue: 51648,
  unitCost: 64805,
  avgInventoryEffectAfterChange: 36044,
  comment: 'hacking',
  newSAPSafetyTime: 65245,
  toSaveUpdates: false,
};

export const sampleWithFullData: IMaterial = {
  id: 43223,
  material: 'payment Synchronised',
  description: 'Paradigm',
  abcClassification: ABCClassification['B'],
  avgSupplierDelay: 63809,
  maxSupplierDelay: 767,
  serviceLevel: 16878,
  plant: 'Small Universal Focused',
  mrpController: 'Bedfordshire',
  currSAPSafetyStock: 97621,
  proposedSST: 11539,
  deltaSST: 22118,
  currentSAPSafeTime: 18608,
  proposedST: 90379,
  deltaST: 28842,
  openSAPmd04: 'Principal cross-platform',
  currentInventoryValue: 94592,
  unitCost: 99763,
  avgDemand: 26016,
  avgInventoryEffectAfterChange: 43479,
  flagMaterial: false,
  flagExpirationDate: dayjs('2023-11-14'),
  comment: 'Avon Direct',
  newSAPSafetyStock: 6767,
  newSAPSafetyTime: 56744,
  lastUpdatedCurrentSS: dayjs('2023-11-14'),
  lastUpdatedCurrentST: dayjs('2023-11-14'),
  toSaveUpdates: true,
  currency: Coin['RUB'],
};

export const sampleWithNewData: NewMaterial = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
