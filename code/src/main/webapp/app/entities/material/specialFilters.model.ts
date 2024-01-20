/**
 * Interface for the SpecialFilter
 */
export interface specialFilter {
    /**
     * name of the specialFilter
     */
    name: string,
    /**
     * Property that keeps track of if the filter is active or not
     */
    isActive: boolean,
    /**
     * List of id numbers of that must be used in the filtering operation
     */
    idList: number[],
}