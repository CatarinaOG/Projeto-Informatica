import { Component, EventEmitter, Input, Output } from '@angular/core';
import { NgbDropdown } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'jhi-flagged-options-bar',
  templateUrl: './flagged-optionsBar.html',
})
export class FlaggedOptionsBar  {

  @Input() tableSize! : number;
  @Output() textFilterEmitter = new EventEmitter<{filterName : string, filterText : string}>();
  @Output() abcFilterEmitter = new EventEmitter<{opType : boolean, filterValue : string}>();
  @Output() tableSizeEmitter = new EventEmitter<number>();
  

  filterStatus = new Map<string,boolean>([
    ["Material Name", false],
    ["Material Description",false],
    ["ABC Classification", false],
    ["Plant",false],
    ["MRP Controller",false],
  ]);
  
  index = 0;
  
  constructor() {}

  changeStatus(name : string) : void {
    for(const pair of this.filterStatus){
      this.filterStatus.set(pair[0], false);
    }
    this.filterStatus.set(name, true);
  }

  checkStatus(): boolean {
    let res = false
    this.filterStatus.forEach((value) => {
      if (value) {res = true}
    })
    return res
  }

  checkDropDown(open:boolean) {
    if(!open){
      for(const pair of this.filterStatus){
        this.filterStatus.set(pair[0], false);
      }
    }
  }

  sendFilterText(event : any) : void {
    if (this.filterStatus.get("Material Name")){
        this.textFilterEmitter.emit({filterName : "Material Name", filterText : event.target.value})
    }
    else if (this.filterStatus.get("Material Description")){
        this.textFilterEmitter.emit({filterName : "Material Description", filterText : event.target.value})      
    }
    else if (this.filterStatus.get("MRP Controller")) {
      this.textFilterEmitter.emit({filterName : "MRP Controller", filterText : event.target.value})    
    }
    else if (this.filterStatus.get("Plant")) {
      this.textFilterEmitter.emit({filterName : "Plant", filterText : event.target.value.toString()})
    }
  }

  sendABCFilter(event : any,  value: string) : void {
    this.abcFilterEmitter.emit({opType: event.target.checked, filterValue : value})
  }

  sendNumber(size : number) : void {
    this.tableSize = size;
    this.tableSizeEmitter.emit(size)
  }

};

