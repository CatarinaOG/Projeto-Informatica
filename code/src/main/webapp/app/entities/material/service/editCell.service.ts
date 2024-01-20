import { Injectable } from '@angular/core';
import { IEditCell } from '../editCell.model';

/**
 * Service responsible for keeping track of which materials have been edited and all the operations required to manipulate said edited materials
 */
@Injectable({ providedIn: 'root' })
export class EditCellService {

    /**
     * Property that stores the materials the user has edited.
     * @type {Map<number,IEditCell>} 
     */
    private materials: Map<number, IEditCell>;

    /**
     * 
     * @constructor
     */
    constructor() {
        this.materials = new Map<number, IEditCell>();
    }

    /**
     * Function that serves as a getter for the materials map
     * @returns The map of currently edited materials
     */
    getMaterials(): Map<number, IEditCell> {
        return this.materials;
    }

    /**
     * Function that adds a material thats been edited to the materials map
     * @param {number} id - id of the material, will serve as key in the materials map 
     * @param {IEditCell} editedMaterial - will serve as value in the materials map
     */
    addMaterial(id: number, editedMaterial: IEditCell): void {
        this.materials.set(id, editedMaterial)
    }

    /**
     * Function that removes a certain material from the materials map
     * @param {number} id - id of the material to be removed 
     */
    removeMaterial(id: number): void {
        this.materials.delete(id)
    }

    /**
     * Setter for the materials map
     * @param cells - cells to be set as the new value of the materials map
     */
    setMaterials(cells: Map<number, IEditCell>): void {
        this.materials = cells
    }

    /**
     * Function that seves as a getter for the size of the materials map
     * @returns map size
     */
    getSize() : number{
        return this.materials.size
    }

    /**
     * Function that serves as a getter for a material that has been edited
     * @param {number} id - id of the material 
     * @returns either the edited material or undefined
     */
    getMaterial(id: number) : IEditCell | undefined{
        return this.materials.get(id)
    }

    /**
     * Function that either selects or un-selects all the edited materials
     * @param {any} event 
     */
    checkUncheckAll(event : any) : void{
        this.materials.forEach(function(value) {
            value.selected = event.target.checked
        })
    }

    /**
     * Function that checks if a material has been edited
     * @param {number} id 
     * @returns 
     */
    hasMaterial (id: number) : boolean{
        return this.materials.has(id)
    }

    /**
     * Function that transforms the current map of materials into a list for the submit operation
     * @returns the list of edited materials
     */
    mapToSubmit(): IEditCell[] {
        const list : IEditCell[] = [];
        this.materials.forEach((value) => {
            if (value.selected) {
                list.push(value);
            }
        })
        return list
    }
    
    /**
     * Function that serves as a getter for filtered lists of the edited materials, either the id's of all the selected materials
     * @param {string} filterOp 
     * @returns 
     */
    getFilteredList(filterOp : string): number[] {
        const res : number[] = []
        this.materials.forEach((linha) => {

            if (linha.selected && filterOp === "Selected"){
                res.push(linha.materialId);
            }
            if (!linha.selected && filterOp === "Unselected"){
                res.push(linha.materialId);
                }
        })
        return res;
    }

    /**
     * Function that serves as a getter for the number of lines that have been edited but not selected
     * @returns 
     */
    getUnselectedLines() : number{
        let count = 0;
        this.materials.forEach((value: IEditCell) => {
            if(!value.selected){
                count++;
            }
        });
        return count;
    }

    /**
     * Function that clears the materials map, to be executed after the user submits changes
     */
    cleanMaterials() : void{
        this.materials.clear();
    }

}
