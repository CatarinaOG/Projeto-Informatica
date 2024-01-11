import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { MaterialComponent } from '../list/material.component';
import { ASC } from 'app/config/navigation.constants';

const materialRoute: Routes = [
  {
    path: '',
    component: MaterialComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(materialRoute)],
  exports: [RouterModule],
})
export class MaterialRoutingModule {}
