import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { PriceCategory } from 'app/shared/model/price-category.model';
import { PriceCategoryService } from './price-category.service';
import { PriceCategoryComponent } from './price-category.component';
import { PriceCategoryDetailComponent } from './price-category-detail.component';
import { PriceCategoryUpdateComponent } from './price-category-update.component';
import { PriceCategoryDeletePopupComponent } from './price-category-delete-dialog.component';
import { IPriceCategory } from 'app/shared/model/price-category.model';

@Injectable({ providedIn: 'root' })
export class PriceCategoryResolve implements Resolve<IPriceCategory> {
  constructor(private service: PriceCategoryService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IPriceCategory> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<PriceCategory>) => response.ok),
        map((priceCategory: HttpResponse<PriceCategory>) => priceCategory.body)
      );
    }
    return of(new PriceCategory());
  }
}

export const priceCategoryRoute: Routes = [
  {
    path: '',
    component: PriceCategoryComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'storageManagementSpringApp.priceCategory.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PriceCategoryDetailComponent,
    resolve: {
      priceCategory: PriceCategoryResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'storageManagementSpringApp.priceCategory.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PriceCategoryUpdateComponent,
    resolve: {
      priceCategory: PriceCategoryResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'storageManagementSpringApp.priceCategory.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PriceCategoryUpdateComponent,
    resolve: {
      priceCategory: PriceCategoryResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'storageManagementSpringApp.priceCategory.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const priceCategoryPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: PriceCategoryDeletePopupComponent,
    resolve: {
      priceCategory: PriceCategoryResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'storageManagementSpringApp.priceCategory.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
