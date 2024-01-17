import { Component, EventEmitter, Input, Output } from '@angular/core';
import { FormBuilder } from '@angular/forms';

@Component({
  selector: 'jhi-filter-form',
  templateUrl: './filterForm.html',
})

export class FilterFormComponent {

  @Input() filterName!: string; 
  @Output() numberFilterEmitter = new EventEmitter<{filterName : string, operator : string, value : number}>();

  numberFilterForm = this.formBuilder.group({
    selectOption: 'equals',
    filterValue: 0
  });

  constructor(private formBuilder: FormBuilder) { }
   
  onSubmit(): void {
        if (this.filterName){
            this.numberFilterEmitter.emit({filterName : this.filterName,
                                            operator : this.numberFilterForm.value.selectOption ?? "", 
                                            value: this.numberFilterForm.value.filterValue ?? 0})
        }

    }

};