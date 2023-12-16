import { Component, inject, TemplateRef, Input, Output, EventEmitter, OnInit } from '@angular/core';
import { NgbDateStruct, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import {formatDate} from '@angular/common';

import { IMaterial } from '../material.model';
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
	@Input() linhas!: Map<number,IEditCell>;
	@Output() flagDateEmmiter = new EventEmitter<{flag : boolean , date : string , id : number}>();

	model: NgbDateStruct | undefined ;
	private modalService = inject(NgbModal);
	date : string = "" ; 
	checkFlag : boolean = false;
	disabled = true;

	ngOnInit(): void {
		const value = this.linhas.get(this.material.id);
		if(value !== undefined){
			this.checkFlag = value.flag;
		}
		else{
			const materialValue = this.material.flagMaterial;
			if (materialValue !== undefined && materialValue !== null){
				this.checkFlag = materialValue;
			}
			else this.checkFlag = false;
		}

	}

	toggleCheckbox() {
	  this.checkFlag = !this.checkFlag;
	}
  
	definePlaceholder() : string{
		let returnValue = "yyyy-mm-dd"
		const lineVal = this.linhas.get(this.material.id)
		if(lineVal !== null && lineVal !== undefined){
			returnValue = lineVal.dateFlag
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

	tooltipDate() : string{
		let returnVal : string = "n/a"
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