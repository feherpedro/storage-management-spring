import { Moment } from 'moment';

export interface IStockTaking {
  id?: number;
  stockTakingDate?: Moment;
  statusId?: number;
}

export class StockTaking implements IStockTaking {
  constructor(public id?: number, public stockTakingDate?: Moment, public statusId?: number) {}
}
