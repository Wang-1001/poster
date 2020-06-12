import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PosterServerSharedModule } from 'app/shared/shared.module';
import { BillTypeComponent } from './bill-type.component';
import { BillTypeDetailComponent } from './bill-type-detail.component';
import { BillTypeUpdateComponent } from './bill-type-update.component';
import { BillTypeDeleteDialogComponent } from './bill-type-delete-dialog.component';
import { billTypeRoute } from './bill-type.route';

@NgModule({
  imports: [PosterServerSharedModule, RouterModule.forChild(billTypeRoute)],
  declarations: [BillTypeComponent, BillTypeDetailComponent, BillTypeUpdateComponent, BillTypeDeleteDialogComponent],
  entryComponents: [BillTypeDeleteDialogComponent],
})
export class PosterServerBillTypeModule {}
