import { Component, inject, TemplateRef, Input, Output, EventEmitter } from '@angular/core';
import { NgbDateStruct, NgbModal } from '@ng-bootstrap/ng-bootstrap';

/**
 * Component responsible for the submit button and the modal that is opened when the user tries to submit the changes
 */
@Component({
	selector: 'jhi-submit-modal',
	templateUrl: './submitModal.html',
})
export class SubmitModalComponent {
	/**
	 * Property where the close result is stored
	 */
    closeResult = '';

	/**
	 * Property passed by the jhi-options-bar component, refers to the number of edited but unselected lines
	 */
	@Input() unselectedLines!: number;

	/**
	 * Property passed by the jhi-options-bar component, refers to the total number of edited lines
	 */
	@Input() totalLines! : number;

	/**
	 * Emitter that allows this component to send the submit confirmation value to the jhi-options-bar component
	 */
	@Output() confirmEmmiter = new EventEmitter<{confirm: boolean}>();

	/**
	 * ModalService property is equal to inject(NgbModal)
	 */
	private modalService = inject(NgbModal);

	/**
	 * Function responsible for the Modal that opens when the user tries to submit the edited values. Depending on the result of the modal, a value will be emitted to the jhi-options-bar component
	 * @param content 
	 */
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