import { Component, EventEmitter, Input, Output } from '@angular/core';

/**
 * Options bar Component for the Flagged Materials Page
 */
@Component({
  selector: 'jhi-flagged-options-bar',
  templateUrl: './flagged-optionsBar.html',
})
export class FlaggedOptionsBarComponent  {

  /**
   * Table Page Size
   */
  @Input() tableSize! : number;

  /**
   * Emitter for filters of type string
   */
  @Output() textFilterEmitter = new EventEmitter<{filterName : string, filterText : string}>();

  /**
   * Emitter for filters of tipe ABC Classification
   */
  @Output() abcFilterEmitter = new EventEmitter<{opType : boolean, filterValue : string}>();

  /**
   * Emitter for the Table Page Size
   */
  @Output() tableSizeEmitter = new EventEmitter<number>();
  

  
  /**
   * Variable that, for each filter available, tells if it's currently active or not
   * @type {Map<string,boolean>}
   */
  filterStatus = new Map<string,boolean>([
    ["Material Name", false],
    ["Material Description",false],
    ["ABC Classification", false],
    ["Plant",false],
    ["MRP Controller",false],
  ]);
  
  /**
   * Function that sets the filter chosen by the user as active
   * @param name Filter name
   */
  changeStatus(name : string) : void {
    for(const pair of this.filterStatus){
      this.filterStatus.set(pair[0], false);
    }
    this.filterStatus.set(name, true);
  }

  /**
   * Function that checks if there's any active filter
   * @returns 
   */
  checkStatus(): boolean {
    let res = false
    this.filterStatus.forEach((value) => {
      if (value) {res = true}
    })
    return res
  }

  /**
   * Function that checks if the dropdown menu is opened.
   * If it's closed, it sets all the filter status to false.
   * @param open Dropdown menu state
   */
  checkDropDown(open:boolean): void {
    if(!open){
      for(const pair of this.filterStatus){
        this.filterStatus.set(pair[0], false);
      }
    }
  }

  /**
   * Function that emits the filter chosen by the user to the parent component
   * @param event 
   */
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


/**
 * Function that emits the abc filter chosen by the user to the parent component
 * @param event 
 * @param value ABC Classification value to filter
 */
  sendABCFilter(event : any,  value: string) : void {
    this.abcFilterEmitter.emit({opType: event.target.checked, filterValue : value})
  }

  /**
   * Function that emits the table page size chosen by the user to the parent component
   * @param size Table page size
   */
  sendNumber(size : number) : void {
    this.tableSize = size;
    this.tableSizeEmitter.emit(size)
  }

};

