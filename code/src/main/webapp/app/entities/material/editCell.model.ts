import { ABCClassification } from "../enumerations/abc-classification.model"

export interface IEditCell {
    materialId : number,
    materialName: string,
    materialDesc: string,
    abcClassification: ABCClassification,
    plant: string,
    mrpcontroller: string,
    newSST: number,
    oldSST: number,
    newST: number,
    oldST: number,
    newComment: string | null,
    oldComment: string | null,
    selected: boolean,
    flag: boolean,
    dateFlag : string
}