import { Moment } from 'moment';

export interface IOrderEntity {
  id?: number;
  createDate?: Moment;
  paymentDate?: Moment;
  dueDate?: Moment;
  statusId?: number;
}

export class OrderEntity implements IOrderEntity {
  constructor(
    public id?: number,
    public createDate?: Moment,
    public paymentDate?: Moment,
    public dueDate?: Moment,
    public statusId?: number
  ) {}
}
