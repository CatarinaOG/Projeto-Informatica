import { Component, EventEmitter, Input, Output } from '@angular/core';
import { FormBuilder } from '@angular/forms';

/**
 * Component responsible for a form for a numeric filter
 */
@Component({
  selector: 'jhi-filter-form',
  templateUrl: './filterForm.html',
})
export class FilterFormComponent {

  /**
   * Property passed by the jhi-options-bar component, name of the column the input filter is going to be applied to 
   * @type {string}
   */
  @Input() filterName!: string; 

  /**
   * Emitter that sends a message containing the name of the column being filtered, the operator and the value of the filter to the jhi-options-bar component
   */
  @Output() numberFilterEmitter = new EventEmitter<{filterName : string, operator : string, value : number}>();

  /**
   * Property that stores the values of the forms where the user inputs the filter data
   */
  numberFilterForm = this.formBuilder.group({
    selectOption: 'equals',
    filterValue: 0
  });

  /**
   * Constructor for the component
   * @constructor
   * @param formBuilder 
   */
  constructor(private formBuilder: FormBuilder) { }
   
  /**
   * Function that executes when the form is submitted, uses the emitter to send the form data to the jhi-options-bar component
   */
  onSubmit(): void {
        if (this.filterName){
            this.numberFilterEmitter.emit({filterName : this.filterName,
                                            operator : this.numberFilterForm.value.selectOption ?? "", 
                                            value: this.numberFilterForm.value.filterValue ?? 0})
        }

    }

};