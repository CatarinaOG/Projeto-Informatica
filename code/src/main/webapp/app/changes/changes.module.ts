import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { RouterModule } from '@angular/router';
import { ChangesComponent } from './changes.component';

@NgModule({
  imports: [SharedModule, RouterModule.forChild([
    {
      path: '',
      component: ChangesComponent,
    },
  ])],
  declarations: [ChangesComponent],
})
export class ChangesPageModule {}