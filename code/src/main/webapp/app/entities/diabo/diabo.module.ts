import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { DiaboComponent } from './list/diabo.component';
import { DiaboDetailComponent } from './detail/diabo-detail.component';
import { DiaboUpdateComponent } from './update/diabo-update.component';
import { DiaboDeleteDialogComponent } from './delete/diabo-delete-dialog.component';
import { DiaboRoutingModule } from './route/diabo-routing.module';

@NgModule({
  imports: [SharedModule, DiaboRoutingModule],
  declarations: [DiaboComponent, DiaboDetailComponent, DiaboUpdateComponent, DiaboDeleteDialogComponent],
})
export class DiaboModule {}
