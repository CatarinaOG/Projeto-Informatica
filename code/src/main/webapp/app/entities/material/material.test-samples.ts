import dayjs from 'dayjs/esm';

import { ABCClassification } from 'app/entities/enumerations/abc-classification.model';
import { Coin } from 'app/entities/enumerations/coin.model';

import { IMaterial, NewMaterial } from './material.model';

export const sampleWithRequiredData: IMaterial = {
  id: 89389,
};

export const sampleWithPartialData: IMaterial = {
  id: 7104,
  material: 'Administrator human-resource',
  avgSupplierDelay: 47908,
  serviceLevel: 21460,
  proposedSST: 86597,
  deltaSST: 64471,
  currentSAPSafeTime: 62991,
  deltaST: 68680,
  openSAPmd04: 'synthesize mint Berkshire',
  currentInventoryValue: 64805,
  unitCost: 36044,
  avgInventoryEffectAfterChange: 14209,
  comment: 'Gloves magnetic payment',
  newSAPSafetyTime: 23192,
  previousSS: 85762,
  datePreviousST: dayjs('2023-11-14'),
  currency: Coin['KRW'],
};

export const sampleWithFullData: IMaterial = {
  id: 92917,
  material: 'payment',
  description: 'Chicken',
  abcClassification: ABCClassification['A'],
  plant: 30369,
  mrpController: 'Legacy',
  avgSupplierDelay: 29936,
  maxSupplierDelay: 36553,
  serviceLevel: 31047,
  currSAPSafetyStock: 48215,
  proposedSST: 26930,
  deltaSST: 24243,
  currentSAPSafeTime: 97621,
  proposedST: 11539,
  deltaST: 22118,
  openSAPmd04: 'Product',
  currentInventoryValue: 91342,
  unitCost: 98257,
  avgDemand: 49824,
  avgInventoryEffectAfterChange: 36060,
  flagMaterial: true,
  flagExpirationDate: dayjs('2023-11-14'),
  comment: 'Engineer Granite Avon',
  newSAPSafetyStock: 89650,
  newSAPSafetyTime: 12181,
  dateNewSS: dayjs('2023-11-13'),
  datNewST: dayjs('2023-11-14'),
  previousSS: 56744,
  previousST: 43315,
  datePreviousSS: dayjs('2023-11-14'),
  datePreviousST: dayjs('2023-11-13'),
  toSaveUpdates: false,
  currency: Coin['CLP'],
};

export const sampleWithNewData: NewMaterial = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
