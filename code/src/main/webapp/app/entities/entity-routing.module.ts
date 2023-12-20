import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'material',
        data: { pageTitle: 'Materials' },
        loadChildren: () => import('./material/material.module').then(m => m.MaterialModule),
      },
      {
        path: 'flagged-material',
        data: { pageTitle: 'FlaggedMaterials' },
        loadChildren: () => import('./flagged-material/flagged-material.module').then(m => m.FlaggedMaterialModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
