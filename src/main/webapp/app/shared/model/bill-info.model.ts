import { Moment } from 'moment';
import { IBillType } from 'app/shared/model/bill-type.model';

export interface IBillInfo {
  id?: number;
  billUserName?: string;
  billPic?: string;
  billWord?: string;
  billAuthor?: string;
  billTime?: Moment;
  billLayoutMode?: string;
  billType?: IBillType;
}

export class BillInfo implements IBillInfo {
  constructor(
    public id?: number,
    public billUserName?: string,
    public billPic?: string,
    public billWord?: string,
    public billAuthor?: string,
    public billTime?: Moment,
    public billLayoutMode?: string,
    public billType?: IBillType
  ) {}
}
