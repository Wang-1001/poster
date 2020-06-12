import { Moment } from 'moment';

export interface IBillType {
  id?: number;
  billTypeName?: string;
  billTypeSort?: number;
  dataTime?: Moment;
}

export class BillType implements IBillType {
  constructor(public id?: number, public billTypeName?: string, public billTypeSort?: number, public dataTime?: Moment) {}
}
