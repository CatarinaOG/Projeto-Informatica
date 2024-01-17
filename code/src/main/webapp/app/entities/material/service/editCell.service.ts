import { Injectable } from '@angular/core';
import { IEditCell } from '../editCell.model';


@Injectable({ providedIn: 'root' })
export class EditCellService {

    private materials: Map<number, IEditCell>;

    constructor() {
        this.materials = new Map<number, IEditCell>();
    }

    getMaterials(): Map<number, IEditCell> {
        return this.materials;
    }

    addMaterial(id: number, cell: IEditCell): void {
        this.materials.set(id, cell)
    }

    removeMaterial(id: number): void {
        this.materials.delete(id)
    }

    setMaterials(cells: Map<number, IEditCell>): void {
        this.materials = cells
    }

    getSize() : number{
        return this.materials.size
    }

    getMaterial(id: number) : IEditCell | undefined{
        return this.materials.get(id)
    }

    getUncheckAll(event : any) : void{
        this.materials.forEach(function(value) {
            value.selected = event.target.checked
        })
    }

    hasMaterial (id: number) : boolean{
        return this.materials.has(id)
    }

    
    mapToSubmit(): IEditCell[] {
        const list : IEditCell[] = [];
        this.materials.forEach((value) => {
            if (value.selected) {
                list.push(value);
            }
        })
        return list
    }
        
    getSelectedList(filterOp : string): number[] {
        const res : number[] = []
        this.materials.forEach((linha) => {
        if(filterOp === "Unedited"){
            res.push(linha.materialId);
        }
        else{
            if (linha.selected && filterOp === "Selected"){

                res.push(linha.materialId);
            }
            if (!linha.selected && filterOp === "Unselected"){
                res.push(linha.materialId);
                }
            }
        })
        return res;
    }

    getUnselectedLines() : number{
        let count = 0;
        this.materials.forEach((value: IEditCell) => {
            if(!value.selected){
                count++;
            }
        });
        return count;
    }


    cleanMaterials() : void{
        this.materials.clear();
    }

}
