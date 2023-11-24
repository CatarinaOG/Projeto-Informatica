import { Component, EventEmitter, OnInit, Input, Output } from '@angular/core';
import { IFilterOption } from 'app/shared/filter/filter.model';

@Component({
  selector: 'filter-display-cell',
  templateUrl: './filterDisplayCell.html',
})

export class FilterDisplayCell implements OnInit {

  @Input() filter!: IFilterOption; 
  @Output() filterRemoveMessage = new EventEmitter<IFilterOption>();

  filterName = "";
  constructor() { }
  

  convertName(filterNameOriginal : string) : string {
    let returnValue ="" ;
    switch (filterNameOriginal) {
      case 'material':
        returnValue = "Material";
        break;
      case 'description':
        returnValue = "Material Description"
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
  ngOnInit(): void {
    this.filterName = this.convertName(this.filter.name.split('.')[0])

   }

  sendFilterRemoveMessage() : void {
    this.filterRemoveMessage.emit(this.filter)
  }

};