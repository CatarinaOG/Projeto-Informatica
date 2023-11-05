import { ABCClassification } from 'app/entities/enumerations/abc-classification.model';

export interface IMaterial {
  id: number;
  material?: number | null;
  description?: string | null;
  abcClassification?: ABCClassification | null;
  avgSupplierDelay?: number | null;
  maxSupplierDelay?: number | null;
  serviceLevel?: number | null;
  currSAPSafetyStock?: number | null;
  propsedSST?: number | null;
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
  comment?: string | null;
}

export type NewMaterial = Omit<IMaterial, 'id'> & { id: null };