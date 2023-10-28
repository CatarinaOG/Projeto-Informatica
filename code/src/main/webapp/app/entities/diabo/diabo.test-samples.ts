import { ABCClassification } from 'app/entities/enumerations/abc-classification.model';

import { IDiabo, NewDiabo } from './diabo.model';

export const sampleWithRequiredData: IDiabo = {
  id: 89913,
};

export const sampleWithPartialData: IDiabo = {
  id: 66813,
  avgSupplierDelayLast90Days: 40093,
  serviceLevel: 85921,
  currentSapSafetyStock: 39760,
  deltaSafetyStock: 79090,
  currentSapSafetyTime: 33940,
  currentInventoryValue: 29083,
  averageInventoryEffectAfterChange: 27011,
  newSapSafetyStock: 41582,
  newSapSafetyTime: 7838,
};

export const sampleWithFullData: IDiabo = {
  id: 64060,
  material: 'Industrial Designer',
  materialDescription: 'Ball Sleek',
  abcClassification: ABCClassification['B'],
  avgSupplierDelayLast90Days: 41397,
  maxSupplierDelayLast90Days: 4399,
  serviceLevel: 67954,
  currentSapSafetyStock: 4968,
  proposedSafetyStock: 99912,
  deltaSafetyStock: 92419,
  currentSapSafetyTime: 79650,
  proposedSafetyTime: 22987,
  deltaSafetyTime: 47201,
  openSapMd04: 'asymmetric',
  currentInventoryValue: 1590,
  averageInventoryEffectAfterChange: 98998,
  newSapSafetyStock: 50515,
  newSapSafetyTime: 89309,
  selectEntriesForChangeInSap: false,
  flagMaterialAsSpecialCase: false,
  commentary: 'evolve quantifying Plastic',
};

export const sampleWithNewData: NewDiabo = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
