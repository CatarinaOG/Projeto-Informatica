import { Component, EventEmitter, OnInit, Input, Output } from '@angular/core';
import { FormBuilder } from '@angular/forms';

@Component({
  selector: 'filter-form',
  templateUrl: './filterForm.html',
})

export class FilterForm implements OnInit {

  @Input() filterName!: string; 
  @Output() numberFilterEmitter = new EventEmitter<{filterName : string, operator : string, value : number}>();

  numberFilterForm = this.formBuilder.group({
    selectOption: 'equals',
    filterValue: 0
  });

  constructor(private formBuilder: FormBuilder) { }
   
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