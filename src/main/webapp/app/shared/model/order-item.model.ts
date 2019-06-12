export interface IOrderItem {
  id?: number;
  quantity?: number;
  orderEntityId?: number;
  productId?: number;
  productName?: string;
  productUnitOfMeasurement?: string;
  productBarcode?: string;
  statusId?: number;
  statusName?: string;
}

export class OrderItem implements IOrderItem {
  constructor(
    public id?: number,
    public quantity?: number,
    public orderEntityId?: number,
    public productId?: number,
    public productName?: string,
    public productUnitOfMeasurement?: string,
    public productBarcode?: string,
    public statusId?: number,
    public statusName?: string
  ) {}
}
