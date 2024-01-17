import { Component, inject, TemplateRef, Input, Output, EventEmitter } from '@angular/core';
import { NgbDateStruct, NgbModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
	selector: 'jhi-submit-modal',
	templateUrl: './submitModal.html',
})
export class SubmitModalComponent {
	
    closeResult = '';

	@Input() unselectedLines!: number;
	@Input() totalLines! : number;
	@Output() confirmEmmiter = new EventEmitter<{confirm: boolean}>();

	model: NgbDateStruct | undefined ;
	private modalService = inject(NgbModal);

	
	open(content: TemplateRef<any>): void {
		if (this.unselectedLines === 0){
			this.confirmEmmiter.emit({confirm : true})
		}
		else {
			this.modalService.open(content, { ariaLabelledBy: 'modal-basic-title' }).result.then(
				(result) => {
					this.closeResult = `Closed with: ${result}`;
					if (result === "Submit Anyways"){
						this.confirmEmmiter.emit({confirm : true})
					}  
					else {this.confirmEmmiter.emit({confirm : false})}
				}, 
				// (reason) => {
				// 	// this.closeResult = `Dismissed ${this.getDismissReason(reason)}`;
				// },
			);
		}
	}


}