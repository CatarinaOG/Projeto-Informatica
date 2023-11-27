import { Component, inject, TemplateRef, Input, Output, EventEmitter } from '@angular/core';
import { NgbDateStruct, NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IMaterial } from '../material.model';

@Component({
	selector: 'flag-modal',
	templateUrl: './flagModal.html',
})
export class FlagModal {
    closeResult = '';

    @Input() material!: IMaterial;
	@Output() flagDataEmmiter = new EventEmitter<string>();

	model: NgbDateStruct | undefined ;
	private modalService = inject(NgbModal);
	date : string = "" ; 

	open(content: TemplateRef<any>): void {
		this.modalService.open(content, { ariaLabelledBy: 'modal-basic-title' }).result.then(
			(result) => {
				this.closeResult = `Closed with: ${result}`;
				console.log("Content is : " , content)
				console.log("Result is", result)
                
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
}