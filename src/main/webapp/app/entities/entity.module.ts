import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'bill-info',
        loadChildren: () => import('./bill-info/bill-info.module').then(m => m.PosterServerBillInfoModule),
      },
      {
        path: 'bill-type',
        loadChildren: () => import('./bill-type/bill-type.module').then(m => m.PosterServerBillTypeModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class PosterServerEntityModule {}
