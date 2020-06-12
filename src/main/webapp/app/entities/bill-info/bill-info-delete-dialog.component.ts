import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBillInfo } from 'app/shared/model/bill-info.model';
import { BillInfoService } from './bill-info.service';

@Component({
  templateUrl: './bill-info-delete-dialog.component.html',
})
export class BillInfoDeleteDialogComponent {
  billInfo?: IBillInfo;

  constructor(protected billInfoService: BillInfoService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.billInfoService.delete(id).subscribe(() => {
      this.eventManager.broadcast('billInfoListModification');
      this.activeModal.close();
    });
  }
}
