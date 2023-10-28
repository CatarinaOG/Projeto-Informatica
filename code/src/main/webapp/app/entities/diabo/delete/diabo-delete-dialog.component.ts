import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IDiabo } from '../diabo.model';
import { DiaboService } from '../service/diabo.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './diabo-delete-dialog.component.html',
})
export class DiaboDeleteDialogComponent {
  diabo?: IDiabo;

  constructor(protected diaboService: DiaboService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.diaboService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
