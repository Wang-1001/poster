import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBillInfo } from 'app/shared/model/bill-info.model';

@Component({
  selector: 'jhi-bill-info-detail',
  templateUrl: './bill-info-detail.component.html',
})
export class BillInfoDetailComponent implements OnInit {
  billInfo: IBillInfo | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ billInfo }) => (this.billInfo = billInfo));
  }

  previousState(): void {
    window.history.back();
  }
}
