import { Component, inject, TemplateRef, Input, Output, EventEmitter, OnInit } from '@angular/core';
import { NgbDateStruct, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import {formatDate} from '@angular/common';

import { IMaterial } from '../material.model';
import { IEditCell } from '../editCell.model';

@Component({
	selector: 'comment-modal',
	templateUrl: './commentModal.html',
})
export class CommentModal  implements OnInit{
	
    closeResult = '';
    comment : string | null | undefined = '';


    @Input() oldComment!: string | null | undefined;
    @Input() id! : number;
	@Output() commentEmitter = new EventEmitter<{newComment : string, id : number}>();

	model: NgbDateStruct | undefined ;
	private modalService = inject(NgbModal);

	ngOnInit(): void {
		this.comment = this.oldComment;
    }

    

	open(content: TemplateRef<any>): void {
		this.modalService.open(content, { ariaLabelledBy: 'modal-basic-title' }).result.then(
			(result) => {
				this.closeResult = `Closed with: ${result}`;
                if (this.comment) this.commentEmitter.emit({newComment: this.comment, id: this.id})
                else  this.commentEmitter.emit({newComment:"", id: this.id})
			}, 
			(reason) => {
				// this.closeResult = `Dismissed ${this.getDismissReason(reason)}`;
                
			},
		);
	}


}