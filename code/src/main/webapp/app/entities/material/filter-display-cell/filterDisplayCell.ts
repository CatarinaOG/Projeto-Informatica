import { Component, EventEmitter, OnInit, Input, Output } from '@angular/core';
import { FilterOptions, IFilterOptions, IFilterOption } from 'app/shared/filter/filter.model';

@Component({
  selector: 'filter-display-cell',
  templateUrl: './filterDisplayCell.html',
})

export class FilterDisplayCell implements OnInit {

  @Input() filter!: IFilterOption; 
  @Output() filterRemoveMessage = new EventEmitter<IFilterOption>();

  constructor() { }
  
  ngOnInit(): void { }

  sendFilterRemoveMessage() : void {
    this.filterRemoveMessage.emit(this.filter)
  }

};