import { Component, inject, TemplateRef, Input, Output, EventEmitter, OnInit } from '@angular/core';
import { NgbDateStruct, NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IMaterial } from '../material.model';
import { IEditCell } from '../editCell.model';

@Component({
	selector: 'submit-modal',
	templateUrl: './submitModal.html',
})
export class SubmitModal  implements OnInit{
	
    closeResult = '';

	@Input() unselectedLines!: number;
	@Input() totalLines! : number;
	@Output() confirmEmmiter = new EventEmitter<{confirm: boolean}>();

	model: NgbDateStruct | undefined ;
	private modalService = inject(NgbModal);

	ngOnInit(): void {

	}
	
	open(content: TemplateRef<any>): void {
		this.modalService.open(content, { ariaLabelledBy: 'modal-basic-title' }).result.then(
			(result) => {
				this.closeResult = `Closed with: ${result}`;
				if (result === "Submit Anyways"){
					this.confirmEmmiter.emit({confirm : true})
				}  
				else this.confirmEmmiter.emit({confirm : false})
			}, 
			(reason) => {
				// this.closeResult = `Dismissed ${this.getDismissReason(reason)}`;
			},
		);
	}




	submitStatus () : boolean {
		let returnVal = false;
		return returnVal;
	}
 

}