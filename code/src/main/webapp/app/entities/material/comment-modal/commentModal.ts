import { Component, inject, TemplateRef, Input, Output, EventEmitter, OnInit } from '@angular/core';
import { NgbDateStruct, NgbModal } from '@ng-bootstrap/ng-bootstrap';


/**
 * Component responsible for showing the user the current comment value and the Modal where the user can edit the comment
 */
@Component({
	selector: 'jhi-comment-modal',
	templateUrl: './commentModal.html',
})
export class CommentModalComponent  implements OnInit{
	
	/**
	 * Property where the close result is stored
	 */
    closeResult = '';

	/**
	 * Property that stores the current comment value
	 */
    comment : string | null | undefined = '';

	/**
	 * Value passed from the jhi-material, corresponds to the comment before the modal has been opened
	 */
    @Input() oldComment!: string | null | undefined;

	/**
	 * Value passed from the jhi-material, the id of the material the comment cell refers to
	 */
    @Input() id! : number;

	/**
	 * Emitter that allows this component to send the newComment value to the jhi-material component
	 */
	@Output() commentEmitter = new EventEmitter<{newComment : string, id : number}>();

	/**
	 * ModalService property is equal to inject(NgbModal)
	 */
	private modalService = inject(NgbModal);

	/**
	 * Function that executes when the component is initialized. the oldComment value passed from the jhi-material component is attributed to the comment property
	 */
	ngOnInit(): void {
		this.comment = this.oldComment;
    }

    
	/**
	 * Function responsible for opening the modal and treating the result after the modal is closed. Emits the edited comment value to the jhi-material component
	 * @param content 
	 */
	open(content: TemplateRef<any>): void {
		this.comment = this.oldComment;
		this.modalService.open(content, { ariaLabelledBy: 'modal-basic-title' }).result.then(
			(result) => {
				this.closeResult = `Closed with: ${result}`;
                if (this.comment) {this.commentEmitter.emit({newComment: this.comment, id: this.id})}
                else  {this.commentEmitter.emit({newComment:"", id: this.id})}
			}, 
			
		);
	}


}