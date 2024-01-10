import dayjs from 'dayjs/esm';
import { ABCClassification } from 'app/entities/enumerations/abc-classification.model';

export interface IFlaggedMaterial {
  id: number;
  material?: string | null;
  description?: string | null;
  abcClassification?: ABCClassification | null;
  plant?: number | null;
  mrpController?: string | null;
  flagMaterial?: boolean | null;
  flagExpirationDate?: dayjs.Dayjs | null;
}

export type NewFlaggedMaterial = Omit<IFlaggedMaterial, 'id'> & { id: null };
