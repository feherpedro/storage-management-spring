import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPriceCategory } from 'app/shared/model/price-category.model';

type EntityResponseType = HttpResponse<IPriceCategory>;
type EntityArrayResponseType = HttpResponse<IPriceCategory[]>;

@Injectable({ providedIn: 'root' })
export class PriceCategoryService {
  public resourceUrl = SERVER_API_URL + 'api/price-categories';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/price-categories';

  constructor(protected http: HttpClient) {}

  create(priceCategory: IPriceCategory): Observable<EntityResponseType> {
    return this.http.post<IPriceCategory>(this.resourceUrl, priceCategory, { observe: 'response' });
  }

  update(priceCategory: IPriceCategory): Observable<EntityResponseType> {
    return this.http.put<IPriceCategory>(this.resourceUrl, priceCategory, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPriceCategory>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPriceCategory[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPriceCategory[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
