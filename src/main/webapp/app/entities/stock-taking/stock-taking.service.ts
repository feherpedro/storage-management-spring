import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IStockTaking } from 'app/shared/model/stock-taking.model';

type EntityResponseType = HttpResponse<IStockTaking>;
type EntityArrayResponseType = HttpResponse<IStockTaking[]>;

@Injectable({ providedIn: 'root' })
export class StockTakingService {
  public resourceUrl = SERVER_API_URL + 'api/stock-takings';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/stock-takings';

  constructor(protected http: HttpClient) {}

  create(stockTaking: IStockTaking): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(stockTaking);
    return this.http
      .post<IStockTaking>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(stockTaking: IStockTaking): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(stockTaking);
    return this.http
      .put<IStockTaking>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IStockTaking>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IStockTaking[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IStockTaking[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(stockTaking: IStockTaking): IStockTaking {
    const copy: IStockTaking = Object.assign({}, stockTaking, {
      stockTakingDate:
        stockTaking.stockTakingDate != null && stockTaking.stockTakingDate.isValid()
          ? stockTaking.stockTakingDate.format(DATE_FORMAT)
          : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.stockTakingDate = res.body.stockTakingDate != null ? moment(res.body.stockTakingDate) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((stockTaking: IStockTaking) => {
        stockTaking.stockTakingDate = stockTaking.stockTakingDate != null ? moment(stockTaking.stockTakingDate) : null;
      });
    }
    return res;
  }
}
