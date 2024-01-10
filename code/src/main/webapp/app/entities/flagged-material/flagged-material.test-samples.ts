import dayjs from 'dayjs/esm';

import { ABCClassification } from 'app/entities/enumerations/abc-classification.model';

import { IFlaggedMaterial, NewFlaggedMaterial } from './flagged-material.model';

export const sampleWithRequiredData: IFlaggedMaterial = {
  id: 71808,
};

export const sampleWithPartialData: IFlaggedMaterial = {
  id: 10990,
  material: 'THX Chips',
  description: 'HDD',
  abcClassification: ABCClassification['C'],
  plant: 46379,
  flagMaterial: false,
};

export const sampleWithFullData: IFlaggedMaterial = {
  id: 15561,
  material: 'Forward Identity',
  description: 'Pants',
  abcClassification: ABCClassification['B'],
  plant: 75870,
  mrpController: 'Fresh',
  flagMaterial: true,
  flagExpirationDate: dayjs('2023-12-20'),
};

export const sampleWithNewData: NewFlaggedMaterial = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
