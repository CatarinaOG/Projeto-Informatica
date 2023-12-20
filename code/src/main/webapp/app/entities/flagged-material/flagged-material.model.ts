import dayjs from 'dayjs/esm';
import { ABCClassification } from 'app/entities/enumerations/abc-classification.model';
import { Coin } from 'app/entities/enumerations/coin.model';

export interface IFlaggedMaterial {
  id: number;
  material?: string | null;
  description?: string | null;
  abcClassification?: ABCClassification | null;
  avgSupplierDelay?: number | null;
  maxSupplierDelay?: number | null;
  serviceLevel?: number | null;
  plant?: string | null;
  mrpController?: string | null;
  currSAPSafetyStock?: number | null;
  proposedSST?: number | null;
  deltaSST?: number | null;
  currentSAPSafeTime?: number | null;
  proposedST?: number | null;
  deltaST?: number | null;
  openSAPmd04?: string | null;
  currentInventoryValue?: number | null;
  unitCost?: number | null;
  avgDemand?: number | null;
  avgInventoryEffectAfterChange?: number | null;
  flagMaterial?: boolean | null;
  flagExpirationDate?: dayjs.Dayjs | null;
  valueOfUpdatedSS?: number | null;
  valueOfUpdatedST?: number | null;
  dateOfUpdatedSS?: dayjs.Dayjs | null;
  dateOfUpdatedST?: dayjs.Dayjs | null;
  toSaveUpdates?: boolean | null;
  currency?: Coin | null;
}

export type NewFlaggedMaterial = Omit<IFlaggedMaterial, 'id'> & { id: null };
