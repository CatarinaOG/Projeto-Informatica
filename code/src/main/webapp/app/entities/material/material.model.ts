import dayjs from 'dayjs/esm';
import { ABCClassification } from 'app/entities/enumerations/abc-classification.model';
import { Coin } from 'app/entities/enumerations/coin.model';

export interface IMaterial {
  id: number;
  material?: string | null;
  description?: string | null;
  abcClassification?: ABCClassification | null;
  plant?: number | null;
  mrpController?: string | null;
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
  flagMaterial?: boolean | null;
  flagExpirationDate?: dayjs.Dayjs | null;
  comment?: string | null;
  newSAPSafetyStock?: number | null;
  newSAPSafetyTime?: number | null;
  dateNewSS?: dayjs.Dayjs | null;
  datNewST?: dayjs.Dayjs | null;
  previousSS?: number | null;
  previousST?: number | null;
  datePreviousSS?: dayjs.Dayjs | null;
  datePreviousST?: dayjs.Dayjs | null;
  toSaveUpdates?: boolean | null;
  currency?: Coin | null;
}

export type NewMaterial = Omit<IMaterial, 'id'> & { id: null };
