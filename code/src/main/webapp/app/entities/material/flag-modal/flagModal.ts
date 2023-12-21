import { Component, inject, TemplateRef, Input, Output, EventEmitter, OnInit } from '@angular/core';
import { NgbDateStruct, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { IMaterial } from '../material.model';
import { EditCellService } from '../service/editCell.service'
import { IEditCell } from '../editCell.model';

@Component({
	selector: 'flag-modal',
	templateUrl: './flagModal.html',
})
export class FlagModal  implements OnInit{
	
    closeResult = '';
	current = new Date();
	minDate = {
	  year: this.current.getFullYear(),
	  month: this.current.getMonth() + 1,
	  day: this.current.getDate()
	};

    @Input() material!: IMaterial;
	@Input() checkFlag! : boolean;
	@Output() flagDateEmmiter = new EventEmitter<{flag : boolean , date : string , id : number}>();

	model: NgbDateStruct | undefined ;
	private modalService = inject(NgbModal);
	date : string = "" ; 
	//checkFlag : boolean = false;
	disabled = true;
	editedMaterial: IEditCell | undefined;

	constructor( protected editCellService: EditCellService ) {}

	ngOnInit(): void {
		this.editedMaterial = this.editCellService.getMaterial(this.material.id);
	
	}

	toggleCheckbox() {
	  this.checkFlag = !this.checkFlag;
	}
  
	definePlaceholder() : string{
		let returnValue = "yyyy-mm-dd"
		if(this.editedMaterial !== null && this.editedMaterial !== undefined){
			returnValue = this.editedMaterial.dateFlag
		}
		return returnValue;
	}

	inputHideShow() : string {
		if (!this.checkFlag) return "tableHide"
		else return ""
	}
	
	open(content: TemplateRef<any>): void {
		this.modalService.open(content, { ariaLabelledBy: 'modal-basic-title' }).result.then(
			(result) => {
				this.closeResult = `Closed with: ${result}`;
                this.flagDateEmmiter.emit({flag : this.checkFlag, date : this.date , id : this.material.id})
			}, 
			(reason) => {
				// this.closeResult = `Dismissed ${this.getDismissReason(reason)}`;
                
			},
		);
	}

	select(model : any) {
		const d = new Date(model);
		console.log("Date is : ", d.toISOString().split("T")[0])
		this.date = d.toISOString().split("T")[0];
	}

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

	submitStatus () : boolean {
		let returnVal = false;
		if(this.checkFlag){
			if(this.date === ""){
				returnVal = true;
			}
			else returnVal = false;
		}
		return returnVal;
	}
 

}