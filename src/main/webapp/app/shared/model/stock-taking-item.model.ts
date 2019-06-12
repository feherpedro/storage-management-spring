export interface IStockTakingItem {
  id?: number;
  oldQuantity?: number;
  newQuantity?: number;
  difference?: number;
  stockTakingId?: number;
  productId?: number;
  statusId?: number;
}

export class StockTakingItem implements IStockTakingItem {
  constructor(
    public id?: number,
    public oldQuantity?: number,
    public newQuantity?: number,
    public difference?: number,
    public stockTakingId?: number,
    public productId?: number,
    public statusId?: number
  ) {}
}
