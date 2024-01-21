import { Component, EventEmitter, OnInit, Input, Output } from '@angular/core';
import { IFilterOption } from 'app/shared/filter/filter.model';
import { specialFilter } from '../specialFilters.model'

/**
 * Component responsible for a cell displaying an active filter
 */
@Component({
  selector: 'jhi-filter-display-cell',
  templateUrl: './filterDisplayCell.html',
})
export class FilterDisplayCellComponent implements OnInit {

  /**
   * Activated filter to be displayed
   * @type {IFilterOption}
   */
  @Input() filter!: IFilterOption; 

  /**
   * Activated special filter to be displayed
   * @type {specialFilter}
   */
  @Input() specialFilter!: specialFilter; 

  /**
   * Emitter that sends the prompt to remove the filter to the jhi-material component
   * @type {EventEmitter<IFilterOption>}
   */
  @Output() filterRemoveMessage = new EventEmitter<IFilterOption>();

  /**
   * Emitter that sends the prompt to remove the special filter to the jhi-material component
   * @type {EventEmitter<IFilterOption>}
   */
  @Output() spFilterRemoveMessage = new EventEmitter<specialFilter>();

  /**
   * Property that stores the filter name
   * @type {string}
   */
  filterName = "";

  /**
   * Property refering to the type of filter being displayed, necessary to distinguish which emitter will be used when the filter is removed
   * @type {boolean}
   */
  flagSpecialFilter = false;
  
  /**
   * Function that converts the filter "code" to a value that can be displayed to the user
   * @param filterNameOriginal 
   * @returns 
   */
  convertName(filterNameOriginal : string) : string {
    let returnValue = "na" ;
    switch (filterNameOriginal) {
      case 'material':
        returnValue = "Material";
        break;
      case 'description':
        returnValue = "Material Description"
        break;
      case 'plant':
        returnValue = 'Plant'
        break;
      case 'mrpController':
        returnValue = 'MRP Controller'
        break;
      case 'abcClassification':
        returnValue = "ABC Classification"
        break;
      case 'avgSupplierDelay':
        returnValue = "Avg Supplier Delay"
        break;
      case 'maxSupplierDelay':
        returnValue = "Max Supplier Delay"
        break;
      case 'serviceLevel':
        returnValue = "Service Level"
        break;
      case 'currSAPSafetyStock':
        returnValue = "Current SAP Safety Stock"
        break;
      case 'proposedSST':
        returnValue = "Proposed SAP Safety Stock"
        break;
      case 'deltaSST':
        returnValue = "Delta SAP Safety Stock"
        break;
      case 'currentSAPSafeTime':
        returnValue = "Current SAP Safety Time"
        break;
      case 'proposedST':
        returnValue = "Proposed SAP Safety Time"
        break;
      case 'deltaST':
        returnValue = "Delta SAP Safety Time"
        break;
      case 'currentInventoryValue':
        returnValue = "Current Inventoy Value"
        break;
      case 'avgInventoryEffectAfterChange':
        returnValue = "Avg Inventory Effect After Change"
        break;
    }
  
    return returnValue
  }

  /**
   * Function that executes when the component is initialized, either turns the flagSpecialFilter property to true or stores the filterName
   */
  ngOnInit(): void {
    if(this.filter){
      this.filterName = this.convertName(this.filter.name.split('.')[0])
    }
    if(this.specialFilter){
      this.flagSpecialFilter = true
    }
   }

  /**
   * Function that sends the message to remove the filter through the appropriate emitter, depending on the type of filter
   */
  sendFilterRemoveMessage() : void {
    if(this.flagSpecialFilter){
      this.spFilterRemoveMessage.emit(this.specialFilter)
    }
    else{
      this.filterRemoveMessage.emit(this.filter)
    }
    
  }

};