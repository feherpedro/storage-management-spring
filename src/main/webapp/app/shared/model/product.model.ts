export interface IProduct {
  id?: number;
  name?: string;
  price?: number;
  quantity?: number;
  unitOfMeasurement?: string;
  barcode?: string;
  productCategoryId?: number;
  priceCategoryId?: number;
  statusId?: number;
}

export class Product implements IProduct {
  constructor(
    public id?: number,
    public name?: string,
    public price?: number,
    public quantity?: number,
    public unitOfMeasurement?: string,
    public barcode?: string,
    public productCategoryId?: number,
    public priceCategoryId?: number,
    public statusId?: number
  ) {}
}
