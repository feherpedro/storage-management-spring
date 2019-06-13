export interface IProduct {
  id?: number;
  name?: string;
  costPrice?: number;
  price?: number;
  quantity?: number;
  unitOfMeasurement?: string;
  manufacturer?: string;
  barcode?: string;
  productCategoryId?: number;
  productCategoryName?: string;
  priceCategoryId?: number;
  priceCategoryName?: string;
  statusId?: number;
  statusName?: string;
}

export class Product implements IProduct {
  constructor(
    public id?: number,
    public name?: string,
    public costPrice?: number,
    public price?: number,
    public quantity?: number,
    public unitOfMeasurement?: string,
    public manufacturer?: string,
    public barcode?: string,
    public productCategoryId?: number,
    public productCategoryName?: string,
    public priceCategoryId?: number,
    public priceCategoryName?: string,
    public statusId?: number,
    public statusName?: string
  ) {}
}
