import { Component, OnInit, OnDestroy } from '@angular/core';
import { Router } from '@angular/router';
import { IEditCell } from 'app/entities/material/editCell.model';
import { EditCellService } from 'app/entities/material/service/editCell.service';

@Component({
  selector: 'jhi-changes-page',
  templateUrl: './changes.component.html',
})

export class ChangesComponent implements OnInit, OnDestroy {

  /**
   * Group of editted Materials submitted to SAP
   * @type {Map<number, IEditCell>}
   */
  materials: Map<number, IEditCell> = new Map<number, IEditCell>();
  
  /**
   * @constructor
   */
  constructor(public router: Router, private editCellService: EditCellService) { }


  /**
   * Returns the materials varible (Map) in Array form
   * @returns {IEditCell[]}
   */
  mapToList(): IEditCell[] {
    const list : IEditCell[] = [];

    this.materials.forEach((value) => {
        if(value.selected){
          list.push(value);
        }
    }
    )
    return list;
  }


  ngOnInit(): void {
      this.materials = this.editCellService.getMaterials()

      if (this.materials.size === 0) {
        this.routeToMaterialsPage()
      }
  }

  ngOnDestroy(): void {
      this.editCellService.cleanMaterials();
  }

  /**
   * Calculates the variation between the original value and the submitted by the user
   * @param {number} oldVal - original value
   * @param {number} newVal - submitted value
   * @returns {number} Variation between oldVal and newVal
   */
  valueVariation(oldVal: number, newVal: number) :number{
      return Math.round(((newVal - oldVal)/oldVal)*100)
  }

  /**
   * Return the absolute number of a value
   * @param {number} value
   * @returns {number} Absolute value
   */
  abs(value: number) :number {
      return Math.abs(value)
  }

  /**
   * Checks if the variation between 2 values was a positive or negative one.
   * If it's positive, returns green. If it's negative it return red
   * @param {number} oldVal
   * @param {number} newVal
   * @returns {string} CSS class name corresponding to the colors (green or red)
   */
  calculateColor(oldVal : number , newVal : number): string{
    const delta = this.valueVariation ( oldVal, newVal)
    if (delta > 0){
      return "textGreen"
    }
    if (delta < 0){
      return "textRed"
    }
    else {
      return ""
    }
  }


  /**
   * Navigates the user ot the Materials Page
   */
  routeToMaterialsPage() : void {
    this.router.navigate(['/material']);
  }
}