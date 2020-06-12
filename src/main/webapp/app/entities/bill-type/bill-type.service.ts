import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IBillType } from 'app/shared/model/bill-type.model';

type EntityResponseType = HttpResponse<IBillType>;
type EntityArrayResponseType = HttpResponse<IBillType[]>;

@Injectable({ providedIn: 'root' })
export class BillTypeService {
  public resourceUrl = SERVER_API_URL + 'api/bill-types';

  constructor(protected http: HttpClient) {}

  create(billType: IBillType): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(billType);
    return this.http
      .post<IBillType>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(billType: IBillType): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(billType);
    return this.http
      .put<IBillType>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IBillType>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IBillType[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(billType: IBillType): IBillType {
    const copy: IBillType = Object.assign({}, billType, {
      dataTime: billType.dataTime && billType.dataTime.isValid() ? billType.dataTime.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dataTime = res.body.dataTime ? moment(res.body.dataTime) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((billType: IBillType) => {
        billType.dataTime = billType.dataTime ? moment(billType.dataTime) : undefined;
      });
    }
    return res;
  }
}
