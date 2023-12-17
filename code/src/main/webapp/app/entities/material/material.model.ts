import dayjs from 'dayjs/esm';
import { ABCClassification } from 'app/entities/enumerations/abc-classification.model';
import { Coin } from 'app/entities/enumerations/coin.model';

export interface IMaterial {
  id: number;
  material?: string | null;
  description?: string | null;
  abcClassification?: ABCClassification | null;
  avgSupplierDelay?: number | null;
  maxSupplierDelay?: number | null;
  serviceLevel?: number | null;
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
  newSAPSafetyStock?: number | null;
  newSAPSafetyTime?: number | null;
  flagMaterial?: boolean | null;
  comment?: string | null;
  flagDate?: dayjs.Dayjs | null;
  plant?: string | null;
  mrpController?: string | null;
  lastEdited?: dayjs.Dayjs | null;
  toSaveLastEdited?: boolean | null;
  currencyType?: Coin | null;
}

export type NewMaterial = Omit<IMaterial, 'id'> & { id: null };
