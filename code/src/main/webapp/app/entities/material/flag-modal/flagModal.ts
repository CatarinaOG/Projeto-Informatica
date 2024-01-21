import { Component, inject, TemplateRef, Input, Output, EventEmitter, OnInit } from '@angular/core';
import { NgbDateStruct, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { IMaterial } from '../material.model';
import { EditCellService } from '../service/editCell.service'
import { IEditCell } from '../editCell.model';

/**
 * Component responsible for the flag cell and the modal used to edit said flag
 */
@Component({
	selector: 'jhi-flag-modal',
	templateUrl: './flagModal.html',
})
export class FlagModalComponent implements OnInit{
	
	model: NgbDateStruct | undefined ;


	/**
	 * Property that stores the result of the close of the modal
	 */
    closeResult = '';

	/**
	 * Property that stores the current date
	 * @type {date}
	 */
	current = new Date();

	/**
	 * The lower limit for the datepicker
	 */
	minDate = {
	  year: this.current.getFullYear(),
	  month: this.current.getMonth() + 1,
	  day: this.current.getDate()
	};

	/**
	 * Material information passed by the jhi-material component
	 * @type {IMaterial}
	 */
    @Input() material!: IMaterial;

	/**
	 * Information about the current state of the flag (possibly after edits), passed by the jhi-material component
	 * @type {boolean}
	 */
	@Input() checkFlag! : boolean;

	/**
	 * Emitter used to send the information about flag edits made in the modal back to the jhi-material component. Sends the current flag state, the date of the 
	 * @type {EventEmitter<{flag : boolean , date : string , id : number}>}
	 */
	@Output() flagDateEmmiter = new EventEmitter<{flag : boolean , date : string , id : number}>();

	/**
	 * Property that stores the date chosen in the date picker
	 * @type {string}
	 */
	date = "" ; 

	/**
	 * Property that stores the edited material, if it exists
	 * @type {IEditCell | undefined}
	 */
	editedMaterial: IEditCell | undefined;

	/**
	 * Property that stores the placeholder text for the datepicker
	 * @type {string}
	 */
	placeholderText = ""

	/**
	 * Injection of NgbModal
	 */
	private modalService = inject(NgbModal);
	

	/**
	 * Constructor for the component
	 * @constructor
	 * @param editCellService 
	 */
	constructor( protected editCellService: EditCellService ) {}


	/**
	 * Function that executes when the component is initialized, checks if the material has been edited and if so, stores the new information in the editedMaterial property, also defines the placeholder
	 */
	ngOnInit(): void {
		this.editedMaterial = this.editCellService.getMaterial(this.material.id);
		this.placeholderText = this.definePlaceholder();
	}

	/**
	 * Function that executes when the flag/unflagged switch is toggled
	 */
	toggleCheckbox() : void {
	  this.checkFlag = !this.checkFlag;
	  if(!this.checkFlag){
		this.date = ""
	  }
	}
  
	/**
	 * Function that defines the placeholder for the date picker
	 */
	definePlaceholder() : string {
		let returnValue = this.material.flagExpirationDate?.toString() ?? "";
		if(this.editedMaterial !== undefined){
			returnValue = this.editedMaterial.dateFlag
		}
		this.defineModel(returnValue)
		return returnValue;
	}

	defineModel(date: string) : void{
		const newDate = new Date(date)
		this.model = {
			day: newDate.getDay(),
			month: newDate.getMonth() - 1,
			year: newDate.getFullYear(),
		}
	}

	/**
	 * Function responsible for the className, determining if the datepicker is hidden or not
	 * @returns the className, tableHide if the datepicker is to be hidden or an empty string if nothing is to happen
	 */
	inputHideShow() : string {
		if (!this.checkFlag) {
			return "tableHide"
		}
		else {
		 return ""
		}
	}
	
	/**
	 * Function that opens the modal and sends the result via the emitter when the modal is closed 
	 * @param content 
	 */
	open(content: TemplateRef<any>): void {
		this.modalService.open(content, { ariaLabelledBy: 'modal-basic-title' }).result.then(
			(result) => {
				this.closeResult = `Closed with: ${result}`;
                this.flagDateEmmiter.emit({flag : this.checkFlag, date : this.date , id : this.material.id})
			},
		);
	}

	/**
	 * Function that executes when a date is selected in the datepicker
	 * @param {any} model 
	 */
	select(model : any) : void {
		const d = new Date(model);
		this.date = d.toISOString().split("T")[0];
	}

	/**
	 * Function that is responsible for setting the text of the tooltip showing the current date value
	 * @returns the date or n/a when there is no date defined
	 */
	tooltipDate() : any{
		let returnVal : any = "n/a"
		if (this.material.flagExpirationDate) {
			returnVal = this.material.flagExpirationDate
		}
		if (this.date !== ""){
			returnVal = this.date
		}
		return returnVal
	}

	/**
	 * Function that determines wether the save/submit button should be disabled or not
	 * @returns {boolean}
	 */
	submitStatus () : boolean {
		let returnVal = false;
		if(this.checkFlag){
			if(this.date === ""){
				returnVal = true;
			}
			else {
				returnVal = false;
			}
		}
		return returnVal;
	}
 

}