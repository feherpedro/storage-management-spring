import { Moment } from 'moment';
import { OrderItem } from 'app/shared/model/order-item.model';

export interface IOrderEntity {
  id?: number;
  createDate?: Moment;
  paymentDate?: Moment;
  dueDate?: Moment;
  statusId?: number;
  statusName?: string;
  orderItemList?: OrderItem[];
}

export class OrderEntity implements IOrderEntity {
  constructor(
    public id?: number,
    public createDate?: Moment,
    public paymentDate?: Moment,
    public dueDate?: Moment,
    public statusId?: number,
    public statusName?: string,
    public orderItemList?: OrderItem[]
  ) {
    this.orderItemList = [];
  }
}
