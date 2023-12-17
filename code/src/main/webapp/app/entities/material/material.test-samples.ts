import dayjs from 'dayjs/esm';

import { ABCClassification } from 'app/entities/enumerations/abc-classification.model';
import { Coin } from 'app/entities/enumerations/coin.model';

import { IMaterial, NewMaterial } from './material.model';

export const sampleWithRequiredData: IMaterial = {
  id: 89389,
};

export const sampleWithPartialData: IMaterial = {
  id: 45159,
  material: 'Account',
  serviceLevel: 7104,
  proposedSST: 54378,
  currentSAPSafeTime: 99662,
  proposedST: 40812,
  deltaST: 97109,
  currentInventoryValue: 26213,
  unitCost: 48603,
  avgDemand: 23536,
  avgInventoryEffectAfterChange: 47908,
  newSAPSafetyTime: 21460,
  flagDate: dayjs('2023-11-13'),
  mrpController: 'payment synthesize',
  currencyType: Coin['XOF'],
};

export const sampleWithFullData: IMaterial = {
  id: 19278,
  material: 'Berkshire',
  description: 'intuitive Gloves',
  abcClassification: ABCClassification['B'],
  avgSupplierDelay: 43223,
  maxSupplierDelay: 59899,
  serviceLevel: 63091,
  currSAPSafetyStock: 52538,
  proposedSST: 81219,
  deltaSST: 23192,
  currentSAPSafeTime: 85762,
  proposedST: 67455,
  deltaST: 30079,
  openSAPmd04: 'migration deposit Taka',
  currentInventoryValue: 30369,
  unitCost: 23649,
  avgDemand: 91194,
  avgInventoryEffectAfterChange: 73324,
  newSAPSafetyStock: 24810,
  newSAPSafetyTime: 29936,
  flagMaterial: false,
  comment: 'Bedfordshire',
  flagDate: dayjs('2023-11-13'),
  plant: 'green',
  mrpController: 'Account',
  lastEdited: dayjs('2023-11-14'),
  toSaveLastEdited: false,
  currencyType: Coin['ANG'],
};

export const sampleWithNewData: NewMaterial = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
