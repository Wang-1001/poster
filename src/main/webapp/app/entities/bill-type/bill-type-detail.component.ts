import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBillType } from 'app/shared/model/bill-type.model';

@Component({
  selector: 'jhi-bill-type-detail',
  templateUrl: './bill-type-detail.component.html',
})
export class BillTypeDetailComponent implements OnInit {
  billType: IBillType | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ billType }) => (this.billType = billType));
  }

  previousState(): void {
    window.history.back();
  }
}
