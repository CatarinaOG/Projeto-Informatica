export interface IHistoryEntity {
    materialId : number,
    oldValue: number | string,
    currentValue: number | string,
    column: string
}