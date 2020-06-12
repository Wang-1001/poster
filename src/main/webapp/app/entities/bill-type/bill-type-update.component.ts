import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IBillType, BillType } from 'app/shared/model/bill-type.model';
import { BillTypeService } from './bill-type.service';

@Component({
  selector: 'jhi-bill-type-update',
  templateUrl: './bill-type-update.component.html',
})
export class BillTypeUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    billTypeName: [],
    billTypeSort: [],
    dataTime: [],
  });

  constructor(protected billTypeService: BillTypeService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ billType }) => {
      if (!billType.id) {
        const today = moment().startOf('day');
        billType.dataTime = today;
      }

      this.updateForm(billType);
    });
  }

  updateForm(billType: IBillType): void {
    this.editForm.patchValue({
      id: billType.id,
      billTypeName: billType.billTypeName,
      billTypeSort: billType.billTypeSort,
      dataTime: billType.dataTime ? billType.dataTime.format(DATE_TIME_FORMAT) : null,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const billType = this.createFromForm();
    if (billType.id !== undefined) {
      this.subscribeToSaveResponse(this.billTypeService.update(billType));
    } else {
      this.subscribeToSaveResponse(this.billTypeService.create(billType));
    }
  }

  private createFromForm(): IBillType {
    return {
      ...new BillType(),
      id: this.editForm.get(['id'])!.value,
      billTypeName: this.editForm.get(['billTypeName'])!.value,
      billTypeSort: this.editForm.get(['billTypeSort'])!.value,
      dataTime: this.editForm.get(['dataTime'])!.value ? moment(this.editForm.get(['dataTime'])!.value, DATE_TIME_FORMAT) : undefined,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBillType>>): void {
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
}
