export interface IStatus {
  id?: number;
  name?: string;
  code?: string;
}

export class Status implements IStatus {
  constructor(public id?: number, public name?: string, public code?: string) {}
}
