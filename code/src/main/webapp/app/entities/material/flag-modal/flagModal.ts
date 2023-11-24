import { Component, inject, TemplateRef, Input } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IMaterial } from '../material.model';

@Component({
	selector: 'flag-modal',
	templateUrl: './flagModal.html',
})
export class FlagModal {
    closeResult = '';

    @Input() material!: IMaterial;


	private modalService = inject(NgbModal);

	open(content: TemplateRef<any>): void {
		this.modalService.open(content, { ariaLabelledBy: 'modal-basic-title' }).result.then(
			(result) => {
				// this.closeResult = `Closed with: ${result}`;
                
			}, 
			(reason) => {
				// this.closeResult = `Dismissed ${this.getDismissReason(reason)}`;
                
			},
		);
	}

    
    
}