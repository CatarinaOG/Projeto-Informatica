import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { FlaggedMaterialComponent } from './list/flagged-material.component';
import { FlaggedMaterialUpdateComponent } from './update/flagged-material-update.component';
import { FlaggedMaterialRoutingModule } from './route/flagged-material-routing.module';
import { MaterialModule } from '../material/material.module';
import { FlaggedOptionsBar } from './options-bar/flagged-optionsBar';

@NgModule({
  imports: [SharedModule, FlaggedMaterialRoutingModule, MaterialModule],
  declarations: [
    FlaggedMaterialComponent,
    FlaggedMaterialUpdateComponent,
    FlaggedOptionsBar,
  ],
})
export class FlaggedMaterialModule {}
