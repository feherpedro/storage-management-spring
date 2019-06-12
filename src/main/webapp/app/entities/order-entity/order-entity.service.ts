import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IOrderEntity } from 'app/shared/model/order-entity.model';

type EntityResponseType = HttpResponse<IOrderEntity>;
type EntityArrayResponseType = HttpResponse<IOrderEntity[]>;

@Injectable({ providedIn: 'root' })
export class OrderEntityService {
  public resourceUrl = SERVER_API_URL + 'api/order-entities';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/order-entities';

  constructor(protected http: HttpClient) {}

  create(orderEntity: IOrderEntity): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(orderEntity);
    return this.http
      .post<IOrderEntity>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(orderEntity: IOrderEntity): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(orderEntity);
    return this.http
      .put<IOrderEntity>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IOrderEntity>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IOrderEntity[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IOrderEntity[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(orderEntity: IOrderEntity): IOrderEntity {
    const copy: IOrderEntity = Object.assign({}, orderEntity, {
      createDate: orderEntity.createDate != null && orderEntity.createDate.isValid() ? orderEntity.createDate.format(DATE_FORMAT) : null,
      paymentDate:
        orderEntity.paymentDate != null && orderEntity.paymentDate.isValid() ? orderEntity.paymentDate.format(DATE_FORMAT) : null,
      dueDate: orderEntity.dueDate != null && orderEntity.dueDate.isValid() ? orderEntity.dueDate.format(DATE_FORMAT) : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.createDate = res.body.createDate != null ? moment(res.body.createDate) : null;
      res.body.paymentDate = res.body.paymentDate != null ? moment(res.body.paymentDate) : null;
      res.body.dueDate = res.body.dueDate != null ? moment(res.body.dueDate) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((orderEntity: IOrderEntity) => {
        orderEntity.createDate = orderEntity.createDate != null ? moment(orderEntity.createDate) : null;
        orderEntity.paymentDate = orderEntity.paymentDate != null ? moment(orderEntity.paymentDate) : null;
        orderEntity.dueDate = orderEntity.dueDate != null ? moment(orderEntity.dueDate) : null;
      });
    }
    return res;
  }
}
