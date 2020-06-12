import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PosterServerSharedModule } from 'app/shared/shared.module';
import { BillInfoComponent } from './bill-info.component';
import { BillInfoDetailComponent } from './bill-info-detail.component';
import { BillInfoUpdateComponent } from './bill-info-update.component';
import { BillInfoDeleteDialogComponent } from './bill-info-delete-dialog.component';
import { billInfoRoute } from './bill-info.route';

@NgModule({
  imports: [PosterServerSharedModule, RouterModule.forChild(billInfoRoute)],
  declarations: [BillInfoComponent, BillInfoDetailComponent, BillInfoUpdateComponent, BillInfoDeleteDialogComponent],
  entryComponents: [BillInfoDeleteDialogComponent],
})
export class PosterServerBillInfoModule {}
