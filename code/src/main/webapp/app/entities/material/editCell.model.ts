import { ABCClassification } from "../enumerations/abc-classification.model"

/**
 * Interface for keeping track of the edited Materials
 */
export interface IEditCell {
    /**
     * Id of the edited material
     */
    materialId : number,
    /**
     * Ñame of the edited Material
     */
    materialName: string,
    /**
     * Description of the edited Material
     */
    materialDesc: string,
    /**
     * ABC Classification of the edited Material
     */
    abcClassification: ABCClassification,
    /**
     * Plant of the edited Material
     */
    plant: number,
    /**
     * MRP Controller of the edited Material
     */
    mrpcontroller: string,
    /**
     * Edited SAP Safety Stock of the Material
     */
    newSST: number,
    /**
     * Original SST of the edited Material
     */
    oldSST: number,
    /**
     * Edited SAP Safety Time of the Material
     */
    newST: number,
    /**
     * Original ST of the edited Material
     */
    oldST: number,
    /**
     * Edited comment of the Material
     */
    newComment: string | null,
    /**
     * Original comment of the edited Material
     */
    oldComment: string | null,
    /**
     * Checks if the Material has been selected for Submission
     */
    selected: boolean,
    /**
     * Original Flag Value of the edited Material
     */
    flag: boolean,
    /**
     * Edited Flag Value of the Material
     */
    newFlag: boolean,
    /**
     * Flag Date of the edited Material
     */
    dateFlag : string
}