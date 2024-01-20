/**
 * Interface for the history of changes made
 */
export interface IHistoryEntity {
    /**
     * Id of the changed material
     */
    materialId : number,
    /**
     * Value before edit was made
     */
    oldValue: number | string,
    /**
     * Value after the edit was made
     */
    currentValue: number | string,
    /**
     * Name of the changed column
     */
    column: string
}