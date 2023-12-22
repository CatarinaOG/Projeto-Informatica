import { Component, OnInit, OnDestroy } from '@angular/core';
import { Router } from '@angular/router';
import { IEditCell } from 'app/entities/material/editCell.model';
import { EditCellService } from 'app/entities/material/service/editCell.service';

@Component({
  selector: 'changes-page',
  templateUrl: './changes.component.html',
})

export class ChangesComponent implements OnInit, OnDestroy {

    materials: Map<number, IEditCell> = new Map<number, IEditCell>();
    

    constructor(public router: Router, private editCellService: EditCellService) { }

    mapToList(): IEditCell[] {

      const list : IEditCell[] = [];

      this.materials.forEach((value,key) => {
          list.push(value);
      }
      )
      return list;
  }


    ngOnInit(): void {
        this.materials = this.editCellService.getMaterials()

        console.log("The materials are : ", this.materials)
    }

    ngOnDestroy(): void {
        this.editCellService.cleanMaterials();
    }

    valueVariation(oldVal: number, newVal: number) {
        return Math.round(((newVal - oldVal)/oldVal)*100)
    }

    abs(value: number) {
        return Math.abs(value)
    }

    calculateColor(oldVal : number , newVal : number): string{
      let delta = this.valueVariation ( oldVal, newVal)
      if (delta > 0){
        return "textGreen"
      }
      if (delta < 0){
        return "textRed"
      }
      else return ""
    }


    
  routeToMaterialsPage() {
    this.router.navigate(['/material']);
  }
}