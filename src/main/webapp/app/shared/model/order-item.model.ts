export interface IOrderItem {
  id?: number;
  quantity?: number;
  orderEntityId?: number;
  productId?: number;
  statusId?: number;
}

export class OrderItem implements IOrderItem {
  constructor(
    public id?: number,
    public quantity?: number,
    public orderEntityId?: number,
    public productId?: number,
    public statusId?: number
  ) {}
}
