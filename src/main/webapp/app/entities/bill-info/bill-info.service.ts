import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IBillInfo } from 'app/shared/model/bill-info.model';

type EntityResponseType = HttpResponse<IBillInfo>;
type EntityArrayResponseType = HttpResponse<IBillInfo[]>;

@Injectable({ providedIn: 'root' })
export class BillInfoService {
  public resourceUrl = SERVER_API_URL + 'api/bill-infos';

  constructor(protected http: HttpClient) {}

  create(billInfo: IBillInfo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(billInfo);
    return this.http
      .post<IBillInfo>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(billInfo: IBillInfo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(billInfo);
    return this.http
      .put<IBillInfo>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IBillInfo>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IBillInfo[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(billInfo: IBillInfo): IBillInfo {
    const copy: IBillInfo = Object.assign({}, billInfo, {
      billTime: billInfo.billTime && billInfo.billTime.isValid() ? billInfo.billTime.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.billTime = res.body.billTime ? moment(res.body.billTime) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((billInfo: IBillInfo) => {
        billInfo.billTime = billInfo.billTime ? moment(billInfo.billTime) : undefined;
      });
    }
    return res;
  }
}
