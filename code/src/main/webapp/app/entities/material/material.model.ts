import dayjs from 'dayjs/esm';
import { ABCClassification } from 'app/entities/enumerations/abc-classification.model';
import { Coin } from 'app/entities/enumerations/coin.model';

/**
 * Interface related to the Material data that is sent from the back-end to populate the table shown to the user
 */
export interface IMaterial {
  /**
   * The id of the Material in the database
   * @type number
   */
  id: number;

  /**
   * The name of the Material in the database
   * @type string | null
   */
  material?: string | null;

  /**
   * The description of the Material in the database
   * @type string | null
   */
  description?: string | null;

  /**
   * The value of the ABC Classification of the Material in the database
   * @type ABCClassification | null
   */
  abcClassification?: ABCClassification | null;

  /**
   * The plant code of the Material in the database
   * @type number | null
   */
  plant?: number | null;

  /**
   * The mrpController value of the Material in the database
   * @type string | null
   */
  mrpController?: string | null;

  /**
   * The Average Supplier Delay value of the Material in the database
   * @type number | null
   */
  avgSupplierDelay?: number | null;

  /**
   * The Maximum Supplier Delay of the Material in the database
   * @type number | null
   */
  maxSupplierDelay?: number | null;

  /**
   * The Service Level value of the Material in the database
   * @type number | null
   */
  serviceLevel?: number | null;

  /**
   * The Current Safety Stock value of the Material in the database
   * @type number | null
   */
  currSAPSafetyStock?: number | null;

  /**
   * The Proposed SAP Safety Stock value of the Material in the database
   * @type number
   */
  proposedSST?: number | null;

  /**
  * The delta value between the Current and Proposed SAP Safety Stock values of the Material in the database
  * @type number | null
  */
  deltaSST?: number | null;

  /**
   * The Current Safety Time value of the Material in the database
   * @type number | null
   */
  currentSAPSafeTime?: number | null;

  /**
   * The Proposed SAP Safety Time value of the Material in the database
   * @type number
   */
  proposedST?: number | null;

  /**
  * The delta value between the Current and Proposed SAP Safety Time values of the Material in the database
  * @type number | null
  */
  deltaST?: number | null;

  /**
   * The openSAPmd04 value of the Material in the database
   * @type string | null;
   */
  openSAPmd04?: string | null;

  /**
   * The Current Inventory Value of the Material in the database
   * @type number | null
   */
  currentInventoryValue?: number | null;

  /**
   * The Unit Cost value of the Material in the database
   * @type number | null
   */
  unitCost?: number | null;

  /**
   * The Average Demand Value of the Material in the database
   * @type number | null
   */
  avgDemand?: number | null;

  /**
   * The Average Inventory Effect After Change value of the Material in the database
   * @type number | null
   */
  avgInventoryEffectAfterChange?: number | null;

  /**
   * Value of the Material in the database refering to the current state of the material as flagged or not
   * @type boolean | null
   */
  flagMaterial?: boolean | null;

  /**
   * The current flag expiration date value of the Material in the database
   * @type dayjs.Dayjs | null
   */
  flagExpirationDate?: dayjs.Dayjs | null;

  /**
   * The comment value of the Material in the database
   * @type string
   */
  comment?: string | null;

  /**
   * The New SAP safety stock value of the Material in the database
   * @type number | null
   */
  newSAPSafetyStock?: number | null;

  /**
   * The New SAP safety stock time of the Material in the database
   * @type number | null
   */
  newSAPSafetyTime?: number | null;

  /**
   * The date of the New SAP Safety Stock value of the Material in the database
   * @type dayjs.Dayjs | null
   */
  dateNewSS?: dayjs.Dayjs | null;

  /**
   * The date of the New SAP Safety Time value of the Material in the database
   * @type dayjs.Dayjs | null
   */
  datNewST?: dayjs.Dayjs | null;

  /**
   * The Previous value of the Safety Stock of the Material in the database
   * @type number
   */
  previousSS?: number | null;

  /**
   * The Previous value of the Safety Stock of the Material in the database
   * @type number
   */
  previousST?: number | null;

  /**
   * The date of the previous SAP Safety Time value of the Material in the database
   * @type dayjs.Dayjs | null
   */
  datePreviousSS?: dayjs.Dayjs | null;

   /**
   * The date of the previous SAP Safety Time value of the Material in the database
   * @type dayjs.Dayjs | null
   */
  datePreviousST?: dayjs.Dayjs | null;

  /**
   * Property used by the back-end to allow the updating of certain materials
   * @type boolean
   */
  toSaveUpdates?: boolean | null;
  
  /**
   * The currency of the Material in the database
   * @type number
   */
  currency?: Coin | null;
}

/**
 * Automatically created by JHipster
 */
export type NewMaterial = Omit<IMaterial, 'id'> & { id: null };
