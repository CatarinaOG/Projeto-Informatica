import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IFlaggedMaterial } from '../flagged-material.model';
import { FlaggedMaterialService } from '../service/flagged-material.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './flagged-material-delete-dialog.component.html',
})
export class FlaggedMaterialDeleteDialogComponent {
  flaggedMaterial?: IFlaggedMaterial;

  constructor(protected flaggedMaterialService: FlaggedMaterialService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.flaggedMaterialService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
