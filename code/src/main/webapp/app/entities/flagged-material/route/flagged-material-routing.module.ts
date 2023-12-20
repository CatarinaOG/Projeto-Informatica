import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { FlaggedMaterialComponent } from '../list/flagged-material.component';
import { FlaggedMaterialDetailComponent } from '../detail/flagged-material-detail.component';
import { FlaggedMaterialUpdateComponent } from '../update/flagged-material-update.component';
import { FlaggedMaterialRoutingResolveService } from './flagged-material-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const flaggedMaterialRoute: Routes = [
  {
    path: '',
    component: FlaggedMaterialComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: FlaggedMaterialDetailComponent,
    resolve: {
      flaggedMaterial: FlaggedMaterialRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: FlaggedMaterialUpdateComponent,
    resolve: {
      flaggedMaterial: FlaggedMaterialRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: FlaggedMaterialUpdateComponent,
    resolve: {
      flaggedMaterial: FlaggedMaterialRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(flaggedMaterialRoute)],
  exports: [RouterModule],
})
export class FlaggedMaterialRoutingModule {}
