import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IBillInfo, BillInfo } from 'app/shared/model/bill-info.model';
import { BillInfoService } from './bill-info.service';
import { IBillType } from 'app/shared/model/bill-type.model';
import { BillTypeService } from 'app/entities/bill-type/bill-type.service';

@Component({
  selector: 'jhi-bill-info-update',
  templateUrl: './bill-info-update.component.html',
})
export class BillInfoUpdateComponent implements OnInit {
  isSaving = false;
  billtypes: IBillType[] = [];

  editForm = this.fb.group({
    id: [],
    billUserName: [],
    billPic: [],
    billWord: [],
    billAuthor: [],
    billTime: [],
    billLayoutMode: [],
    billType: [],
  });

  constructor(
    protected billInfoService: BillInfoService,
    protected billTypeService: BillTypeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ billInfo }) => {
      if (!billInfo.id) {
        const today = moment().startOf('day');
        billInfo.billTime = today;
      }

      this.updateForm(billInfo);

      this.billTypeService.query().subscribe((res: HttpResponse<IBillType[]>) => (this.billtypes = res.body || []));
    });
  }

  updateForm(billInfo: IBillInfo): void {
    this.editForm.patchValue({
      id: billInfo.id,
      billUserName: billInfo.billUserName,
      billPic: billInfo.billPic,
      billWord: billInfo.billWord,
      billAuthor: billInfo.billAuthor,
      billTime: billInfo.billTime ? billInfo.billTime.format(DATE_TIME_FORMAT) : null,
      billLayoutMode: billInfo.billLayoutMode,
      billType: billInfo.billType,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const billInfo = this.createFromForm();
    if (billInfo.id !== undefined) {
      this.subscribeToSaveResponse(this.billInfoService.update(billInfo));
    } else {
      this.subscribeToSaveResponse(this.billInfoService.create(billInfo));
    }
  }

  private createFromForm(): IBillInfo {
    return {
      ...new BillInfo(),
      id: this.editForm.get(['id'])!.value,
      billUserName: this.editForm.get(['billUserName'])!.value,
      billPic: this.editForm.get(['billPic'])!.value,
      billWord: this.editForm.get(['billWord'])!.value,
      billAuthor: this.editForm.get(['billAuthor'])!.value,
      billTime: this.editForm.get(['billTime'])!.value ? moment(this.editForm.get(['billTime'])!.value, DATE_TIME_FORMAT) : undefined,
      billLayoutMode: this.editForm.get(['billLayoutMode'])!.value,
      billType: this.editForm.get(['billType'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBillInfo>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: IBillType): any {
    return item.id;
  }
}
