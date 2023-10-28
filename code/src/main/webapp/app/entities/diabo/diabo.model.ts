import { ABCClassification } from 'app/entities/enumerations/abc-classification.model';

export interface IDiabo {
  id: number;
  material?: string | null;
  materialDescription?: string | null;
  abcClassification?: ABCClassification | null;
  avgSupplierDelayLast90Days?: number | null;
  maxSupplierDelayLast90Days?: number | null;
  serviceLevel?: number | null;
  currentSapSafetyStock?: number | null;
  proposedSafetyStock?: number | null;
  deltaSafetyStock?: number | null;
  currentSapSafetyTime?: number | null;
  proposedSafetyTime?: number | null;
  deltaSafetyTime?: number | null;
  openSapMd04?: string | null;
  currentInventoryValue?: number | null;
  averageInventoryEffectAfterChange?: number | null;
  newSapSafetyStock?: number | null;
  newSapSafetyTime?: number | null;
  selectEntriesForChangeInSap?: boolean | null;
  flagMaterialAsSpecialCase?: boolean | null;
  commentary?: string | null;
}

export type NewDiabo = Omit<IDiabo, 'id'> & { id: null };
