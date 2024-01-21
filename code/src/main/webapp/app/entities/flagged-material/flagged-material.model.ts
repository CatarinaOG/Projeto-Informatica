import dayjs from 'dayjs/esm';
import { ABCClassification } from 'app/entities/enumerations/abc-classification.model';

/**
 * Interface related to the Flagged Material data that is sent from the back-end to populate the table shown to the user
 */
export interface IFlaggedMaterial {
  /**
   * The id of the Material in the database
   * @type {number}
   */
  id: number;
  
  /**
   * The name of the Material in the database
   * @type {string | null}
   */
  material?: string | null;

  /**
   * The description of the Material in the database
   * @type {string | null}
   */
  description?: string | null;

  /**
   * The value of the ABC Classification of the Material in the database
   * @type {ABCClassification | null}
   */
  abcClassification?: ABCClassification | null;

  /**
   * The plant code of the Material in the database
   * @type {number | null}
   */
  plant?: number | null;

  /**
   * The mrpController value of the Material in the database
   * @type {string | null}
   */
  mrpController?: string | null;

  /**
   * Value of the Material in the database refering to the current state of the material as flagged or not
   * @type {boolean | null}
   */
  flagMaterial?: boolean | null;

  /**
   * The current flag expiration date value of the Material in the database
   * @type {dayjs.Dayjs | null}
   */
  flagExpirationDate?: dayjs.Dayjs | null;
}

export type NewFlaggedMaterial = Omit<IFlaggedMaterial, 'id'> & { id: null };
