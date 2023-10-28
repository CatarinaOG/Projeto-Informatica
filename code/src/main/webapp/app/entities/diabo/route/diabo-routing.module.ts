import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { DiaboComponent } from '../list/diabo.component';
import { DiaboDetailComponent } from '../detail/diabo-detail.component';
import { DiaboUpdateComponent } from '../update/diabo-update.component';
import { DiaboRoutingResolveService } from './diabo-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const diaboRoute: Routes = [
  {
    path: '',
    component: DiaboComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DiaboDetailComponent,
    resolve: {
      diabo: DiaboRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DiaboUpdateComponent,
    resolve: {
      diabo: DiaboRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DiaboUpdateComponent,
    resolve: {
      diabo: DiaboRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(diaboRoute)],
  exports: [RouterModule],
})
export class DiaboRoutingModule {}
