export interface IPriceCategory {
  id?: number;
  name?: string;
  price?: number;
}

export class PriceCategory implements IPriceCategory {
  constructor(public id?: number, public name?: string, public price?: number) {}
}
