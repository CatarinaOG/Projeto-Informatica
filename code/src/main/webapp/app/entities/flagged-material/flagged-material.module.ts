import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { FlaggedMaterialComponent } from './list/flagged-material.component';
import { FlaggedMaterialDetailComponent } from './detail/flagged-material-detail.component';
import { FlaggedMaterialUpdateComponent } from './update/flagged-material-update.component';
import { FlaggedMaterialDeleteDialogComponent } from './delete/flagged-material-delete-dialog.component';
import { FlaggedMaterialRoutingModule } from './route/flagged-material-routing.module';

@NgModule({
  imports: [SharedModule, FlaggedMaterialRoutingModule],
  declarations: [
    FlaggedMaterialComponent,
    FlaggedMaterialDetailComponent,
    FlaggedMaterialUpdateComponent,
    FlaggedMaterialDeleteDialogComponent,
  ],
})
export class FlaggedMaterialModule {}
