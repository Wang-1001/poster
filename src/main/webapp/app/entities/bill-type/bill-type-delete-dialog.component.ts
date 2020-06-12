import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBillType } from 'app/shared/model/bill-type.model';
import { BillTypeService } from './bill-type.service';

@Component({
  templateUrl: './bill-type-delete-dialog.component.html',
})
export class BillTypeDeleteDialogComponent {
  billType?: IBillType;

  constructor(protected billTypeService: BillTypeService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.billTypeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('billTypeListModification');
      this.activeModal.close();
    });
  }
}
