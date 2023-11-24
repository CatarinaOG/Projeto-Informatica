import { Component, EventEmitter, OnInit, Input, Output } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { FilterOptions, IFilterOptions, IFilterOption } from 'app/shared/filter/filter.model';

@Component({
  selector: 'filter-form',
  templateUrl: './filterForm.html',
})

export class FilterForm implements OnInit {

  @Input() filterName!: string; 
  @Output() numberFilterEmitter = new EventEmitter<{filterName : string, operator : string, value : number}>();

  constructor(private formBuilder: FormBuilder) { }
     
  numberFilterForm = this.formBuilder.group({
    selectOption: '==',
    filterValue: 0
  });
   
  ngOnInit(): void { }
  onSubmit(): void {
        console.log("Formulario: " , this.numberFilterForm.value);
        if (this.filterName){
            this.numberFilterEmitter.emit({filterName : this.filterName,
                                            operator : this.numberFilterForm.value.selectOption ?? "", 
                                            value: this.numberFilterForm.value.filterValue ?? 0})
        }

    }

};